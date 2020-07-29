package com.moonyue.sleeve.service;

import cn.hutool.core.util.StrUtil;
import com.moonyue.sleeve.common.util.EncryptUtil;
import com.moonyue.sleeve.core.exception.ForbiddenException;
import com.moonyue.sleeve.core.exception.NotFoundException;
import com.moonyue.sleeve.dto.RegisterDTO;
import com.moonyue.sleeve.model.User;
import com.moonyue.sleeve.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void createUser(RegisterDTO dto){

        this.userRepository.findFirstByNickname(dto.getNickname())
                .ifPresent((user) -> {
                    throw new ForbiddenException(10071);
                });

        if(StrUtil.isNotBlank(dto.getEmail())){
            this.userRepository.findFirstByEmail(dto.getEmail())
                    .ifPresent((user) -> {
                        throw new ForbiddenException(10076);
                    });
        }else{
            // bug 前端如果传入的email为 "" 时，由于数据库中存在""的email，会报duplication错误
            // 所以如果email为blank，必须显示设置为 null
            dto.setEmail(null);
        }

        User user = new User();
        BeanUtils.copyProperties(dto, user);
        this.userRepository.save(user);
    }

    public User verifyNicknamePassword(String nickname, String password){
        User user = this.userRepository.findFirstByNickname(nickname)
                .orElseThrow(() -> new ForbiddenException(20005));

        Boolean isValid = user.verifyPassword(password);
        if(!isValid){
            throw new ForbiddenException(20005);
        }
        return user;
    }
}
