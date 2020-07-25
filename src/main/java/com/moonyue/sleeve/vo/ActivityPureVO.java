package com.moonyue.sleeve.vo;

import com.moonyue.sleeve.model.Activity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.sql.Date;

@Getter
@Setter
public class ActivityPureVO {

    private Long id;
    private String title;
    private String description;
    private Date startTime;
    private Date endTime;
    private String remark;
    private Boolean online;
    private String entranceImg;
    private String internalTopImg;
    private String name;

    public ActivityPureVO(Activity activity){
        BeanUtils.copyProperties(activity, this);
    }

}
