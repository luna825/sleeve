package com.moonyue.sleeve.api.v1;


import com.moonyue.sleeve.core.exception.NotFoundException;
import com.moonyue.sleeve.dto.PersonDTO;
import com.moonyue.sleeve.vo.CreatedVO;
import com.moonyue.sleeve.vo.DeletedVO;
import com.moonyue.sleeve.vo.UpdatedVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;


@RestController
@RequestMapping("api/v1/test")
@Validated
public class TestApiController {

    @GetMapping("index")
    public String index() throws Exception {
        int a=1;
        int b = a/0;
        return "hello, spring";
    }

    @GetMapping("/http_exception")
    public String httpE(){
        throw new NotFoundException(20004, "什么也没有");
//        return "hello, http_exception";
    }

    @PostMapping("vail/{id}")
    public PersonDTO vail(
            @PathVariable @Max(10) Integer id,
            @RequestParam String name,
            @RequestBody(required = false) @Validated PersonDTO p
    ){
        System.out.println(id);
        System.out.println(name);
        return p;
    }

    @PostMapping("/aspect/created")
    public CreatedVO created(){
        return new CreatedVO(11);
    }

    @PostMapping("/aspect/updated")
    public UpdatedVO updated(){
        return new UpdatedVO("uuuuuu");
    }

    @PostMapping("/aspect/deleted")
    public DeletedVO deleted(){
        return new DeletedVO("ddddddd");
    }

}
