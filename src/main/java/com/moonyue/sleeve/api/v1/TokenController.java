package com.moonyue.sleeve.api.v1;


import com.moonyue.sleeve.dto.TokenGetDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("api/v1/token")
@Validated
public class TokenController {

    @PostMapping("")
    public Map<String, String> getToken(@RequestBody @Valid TokenGetDTO dto){
        return null;
    }
}
