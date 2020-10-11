package com.moonyue.sleeve.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Getter
@Setter
@Where(clause = "delete_time is null")
public class SpuDetailImg extends BaseEntity {
    @Id
    private Long id;
    private String img;
    private Long spuId;
    private int index;
}
