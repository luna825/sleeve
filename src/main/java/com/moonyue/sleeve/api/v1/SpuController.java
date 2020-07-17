package com.moonyue.sleeve.api.v1;

import com.moonyue.sleeve.core.exception.NotFoundException;
import com.moonyue.sleeve.model.Spu;
import com.moonyue.sleeve.service.SpuService;
import com.moonyue.sleeve.vo.SpuSimplifyVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/spu")
@Validated
public class SpuController {

    @Autowired
    private SpuService spuService;

    @GetMapping("/id/{id}/detail")
    public Spu getDetail(@PathVariable @Positive Long id){
        return spuService.getById(id).orElseThrow(() -> new NotFoundException(30003));
    }

    @GetMapping("/latest")
    public List<SpuSimplifyVO> getLatestSpuList(){
        List<Spu> spuList = this.spuService.getLatestPagingSpu();
        return spuList.stream().map(s-> {
            SpuSimplifyVO vo = new SpuSimplifyVO();
            BeanUtils.copyProperties(s, vo);
            return vo;
        }).collect(Collectors.toList());
    }
}
