package com.moonyue.sleeve.api.v1;


import com.moonyue.sleeve.core.exception.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class BannerController {

    @GetMapping("test")
    public String test() throws Exception {
        int a=1;
        int b = a/0;
        return "hello, spring";
    }

    @GetMapping("/http_exception")
    public String httpE(){
        throw new NotFoundException(20004, "什么也没有");
//        return "hello, http_exception";
    }
}
