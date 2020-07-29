package com.moonyue.sleeve.common.aop;

import cn.hutool.core.util.StrUtil;
import com.moonyue.sleeve.common.configuration.CodeMessageConfiguration;
import com.moonyue.sleeve.vo.UnifyResponseVO;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ResultAspect {

    @AfterReturning(returning ="result", pointcut = "execution(public * com.moonyue.sleeve.api..*.*(..))")
    public void doAfterReturning(UnifyResponseVO<String> result){
        Integer code = result.getCode();
        String oldMessage = result.getMessage();
        String newMessage = CodeMessageConfiguration.getMessage(code);
        // 如果 code-message.properties 中指定了相应的 message 并且 UnifyResponseVO 的 message 为null
        // 则使用 newMessage 替换 oldMessage
        if (StrUtil.isNotBlank(newMessage) && StrUtil.isBlank(oldMessage)) {
            result.setMessage(newMessage);
        }
    }

}
