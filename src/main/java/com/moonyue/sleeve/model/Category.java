package com.moonyue.sleeve.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Setter
@Getter
public class Category extends BaseEntity {
    @Id
    private Long id;
    private String name;
    private String description;
    private Boolean isRoot;
    private Long parentId;
    private String img;
    private Long index;
    private Boolean online;
    private Long level;

    @ManyToMany
    @JoinTable(name = "coupon_category", joinColumns = @JoinColumn(name="category_id"),
    inverseJoinColumns = @JoinColumn(name = "coupon_id"))
    private List<Coupon> couponList;
}
