package com.moonyue.sleeve.core.interceptors;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.interfaces.Claim;
import com.moonyue.sleeve.common.util.JwtToken;
import com.moonyue.sleeve.core.LocalUser;
import com.moonyue.sleeve.core.annotations.ScopeLevel;
import com.moonyue.sleeve.core.exception.AuthenticationException;
import com.moonyue.sleeve.core.exception.ForbiddenException;
import com.moonyue.sleeve.core.exception.NotFoundException;
import com.moonyue.sleeve.model.User;
import com.moonyue.sleeve.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

public class PermissionInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserRepository userRepository;

    public PermissionInterceptor() {
        super();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Optional<ScopeLevel> scopeLevel = this.getScopeLevel(handler);
        // 没ScopeLevel注解的controller是公开api,直接进入
        if(!scopeLevel.isPresent()){
            return true;
        }
        //有ScopeLevel注解的情况
        String bearerToken = request.getHeader("Authorization");
        if(StrUtil.isEmpty(bearerToken)){
            // 没传token ，相当于没有登录
            throw new AuthenticationException(10004);
        }
        if(!bearerToken.startsWith("Bearer")){
            // 令牌不合法
            throw new AuthenticationException(10004);
        }

        String[] tokens = bearerToken.split(" ");
        if (!(tokens.length == 2)) {
            throw new AuthenticationException(10004);
        }

        Map<String, Claim> map = JwtToken.getClaim(tokens[1]).orElseThrow(() -> new AuthenticationException(10004));
        Boolean valid =  this.hasPermission(map, scopeLevel.get());
        if(valid){
            this.setToThreadLocal(map);
        }
        return valid;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LocalUser.clear();
        super.afterCompletion(request, response, handler, ex);
    }

    private Optional<ScopeLevel> getScopeLevel(Object handler){
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            ScopeLevel scopeLevel =null;

            scopeLevel = handlerMethod.getMethodAnnotation(ScopeLevel.class);
            if (scopeLevel != null) {
                return Optional.of(scopeLevel);
            }

            scopeLevel = handlerMethod.getBeanType().getAnnotation(ScopeLevel.class);

            if(scopeLevel != null){
                return Optional.of(scopeLevel);
            }
        }
        return Optional.empty();
    }

    private Boolean hasPermission(Map<String, Claim> map, ScopeLevel scopeLevel){
        int level = scopeLevel.level();
        int scope = map.get("scope").asInt();
        if(level > scope){
            throw new ForbiddenException(10005);
        }
        return true;
    }

    private void setToThreadLocal(Map<String, Claim> map){
        Long uid = map.get("uid").asLong();
        Integer scope = map.get("scope").asInt();

        User user = this.userRepository.findById(uid).orElseThrow(()->new NotFoundException(20002));
        LocalUser.setLocalUser(user, scope);
    }
}
