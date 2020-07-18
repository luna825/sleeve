package com.moonyue.sleeve.api.v1;

import com.moonyue.sleeve.bo.PageCounter;
import com.moonyue.sleeve.common.util.CommonUtil;
import com.moonyue.sleeve.core.exception.NotFoundException;
import com.moonyue.sleeve.model.Spu;
import com.moonyue.sleeve.service.SpuService;
import com.moonyue.sleeve.vo.PagingDozer;
import com.moonyue.sleeve.vo.SpuSimplifyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;


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
    public PagingDozer<Spu, SpuSimplifyVO> getLatestSpuList(
            @RequestParam(defaultValue = "0") Integer start,
            @RequestParam(defaultValue = "10") Integer count
    ){
        PageCounter pageCounter = CommonUtil.convertToPageParameter(start, count);
        Page<Spu> spuList = this.spuService.getLatestPagingSpu(pageCounter.getPage(), pageCounter.getSize());
        return new PagingDozer<>(spuList, SpuSimplifyVO.class);
    }
}
