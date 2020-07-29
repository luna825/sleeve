package com.moonyue.sleeve.api.v1;


import com.moonyue.sleeve.common.util.JwtToken;
import com.moonyue.sleeve.core.exception.NotFoundException;
import com.moonyue.sleeve.dto.TokenDTO;
import com.moonyue.sleeve.dto.TokenGetDTO;
import com.moonyue.sleeve.model.TestUser;
import com.moonyue.sleeve.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/token")
@Validated
public class TokenController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("")
    public Map<String, String> getToken(@RequestBody @Valid TokenGetDTO dto){
        Map<String, String> map = new HashMap<>();
        String token = null;
        switch (dto.getLoginType()){
            case USER_WX:
                token = authenticationService.code2Session(dto.getAccount());
                break;
            case USER_NAME:
                token = authenticationService.getTokenByNickname(dto);
                break;
            default:
                throw new NotFoundException(10003);
        }
        map.put("token", token);
        return map;
    }

    @PostMapping("verify")
    public Map<String, Boolean> verify(@RequestBody @Valid TokenDTO dto){
        Map<String, Boolean> map = new HashMap<>();
        Boolean isValid = JwtToken.verifyToken(dto.getToken());
        map.put("is_valid", isValid);
        return map;
    }
}
