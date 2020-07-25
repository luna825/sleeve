package com.moonyue.sleeve.model;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.sql.Date;
import java.util.List;

@Entity
@Setter
@Getter
public class Activity extends BaseEntity {
    @Id
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

    @OneToMany()
    @JoinColumn(name="activityId")
    private List<Coupon> couponList;

}
