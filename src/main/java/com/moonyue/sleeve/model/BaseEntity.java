package com.moonyue.sleeve.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {
    @JsonIgnore
    @CreationTimestamp
    @Column(updatable = false)
    private Date createTime;

    @JsonIgnore
    @UpdateTimestamp
    private Date updateTime;

    @JsonIgnore
    private Date deleteTime;
}
