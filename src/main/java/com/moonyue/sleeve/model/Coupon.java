package com.moonyue.sleeve.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Setter
@Getter
public class Coupon extends BaseEntity {
    @Id
    private Long id;
    private String title;
    private Date startTime;
    private Date endTime;
    private String description;
    private BigDecimal fullMoney;
    private BigDecimal minus;
    private BigDecimal rate;
    private Integer type;
    private Long activityId;
    private String remark;
    private Boolean wholeStore;

    @ManyToMany(mappedBy = "couponList")
    private List<Category> categoryList;

}
