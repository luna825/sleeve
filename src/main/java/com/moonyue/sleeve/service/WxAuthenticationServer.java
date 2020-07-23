package com.moonyue.sleeve.service;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moonyue.sleeve.common.util.JwtToken;
import com.moonyue.sleeve.core.exception.ParameterException;
import com.moonyue.sleeve.model.User;
import com.moonyue.sleeve.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class WxAuthenticationServer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Value("${wx.appid}")
    private String appId;

    @Value("${wx.appsecret}")
    private String appSecret;

    @Value("${wx.code2session}")
    private String code2SessionUrl;

    public String code2Session(String code){
        String url = MessageFormat.format(code2SessionUrl, appId, appSecret, code);
        Map<String, String> session = new HashMap<>();
        RestTemplate rest = new RestTemplate();
        String sessionText = rest.getForObject(url, String.class);
        try {
            session = objectMapper.readValue(sessionText, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return this.registerUser(session);
    }

    private String registerUser(Map<String, String> session){
        String openId = session.get("openid");
        if(StrUtil.isEmpty(openId)){
            throw new ParameterException(20004);
        }
        Optional<User> userOptional = this.userRepository.findByOpenid(openId);
        if(userOptional.isPresent()){
            // 返回jwt令牌
            return JwtToken.makeToken(userOptional.get().getId());
        }
        User user = User.builder().openid(openId).build();
        this.userRepository.save(user);
        Long uid = user.getId();
        // 返回jwt令牌
        return JwtToken.makeToken(uid);
    }
}
