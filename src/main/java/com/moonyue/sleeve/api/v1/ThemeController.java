package com.moonyue.sleeve.api.v1;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.moonyue.sleeve.core.exception.NotFoundException;
import com.moonyue.sleeve.model.Theme;
import com.moonyue.sleeve.service.ThemeService;
import com.moonyue.sleeve.vo.ThemePureVO;
import com.moonyue.sleeve.vo.ThemeSpuVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/theme")
@Validated
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    @GetMapping("/by/names")
    public List<ThemePureVO> getByNames(@RequestParam @NotBlank String names){
        List<String> nameList = Arrays.asList(names.split(","));
        return this.themeService.getByNames(nameList).stream()
                .map(t ->{
                    ThemePureVO vo = new ThemePureVO();
                    BeanUtils.copyProperties(t, vo);
                    return vo;
                }).collect(Collectors.toList());
    }

    @GetMapping("name/{name}/with_spu")
    public ThemeSpuVO getByNameWithSpu(@PathVariable String name){
        Theme theme = this.themeService.getByName(name).orElseThrow(() -> new NotFoundException(30003));
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        return mapper.map(theme, ThemeSpuVO.class);
    }

}
