package com.moonyue.sleeve.model;


import cn.hutool.core.util.StrUtil;
import com.moonyue.sleeve.common.util.JwtToken;
import com.moonyue.sleeve.core.exception.AuthorizationException;
import com.moonyue.sleeve.core.exception.ParameterException;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
public class TestUser {
    private Long id;
    private String username;
    private String password;


    private TestUser(Builder builder){
        this.id = builder.id;
        this.username = builder.username;
        this.password = builder.password;
    }

    public static class Builder {
        // 默认值
        private static final Long ID = 9L;
        // 需要设置的值
        private Long id = ID;
        private String username;
        private String password;

        public TestUser build(){
            // 校验逻辑
            if(!this.username.equals("test")){
                throw new AuthorizationException(20002);
            }

            if(!this.password.equals("123")){
                throw new AuthorizationException(20003);
            }

            return new TestUser(this);
        }

        public Builder setId(Long id){
            this.id = id;
            return this;
        }

        public Builder setUsername(String username){
            if (StrUtil.isEmpty(username)){
                throw new ParameterException(20000, "用户名不能为空");
            }
            this.username = username;
            return this;
        }

        public Builder setPassword(String password){
            this.password = password;
            return this;
        }
    }

    public String generateToken(){
        return JwtToken.makeToken(this.id);
    }
}
