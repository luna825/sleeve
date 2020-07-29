package com.moonyue.sleeve.api.v1;

import com.moonyue.sleeve.dto.RegisterDTO;
import com.moonyue.sleeve.service.UserService;
import com.moonyue.sleeve.vo.CreatedVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("api/v1/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("register")
    public CreatedVO register(@RequestBody @Valid RegisterDTO dto){
        this.userService.createUser(dto);
        return new CreatedVO(11);
    }

}
