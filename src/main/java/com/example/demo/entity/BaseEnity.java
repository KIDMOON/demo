package com.example.demo.entity;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@MappedSuperclass
public class BaseEnity {

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(
            name = "created_date"
    )
    private Date createDate;

    @Column(name = "delecetd")
    private Boolean delecetd;
}
