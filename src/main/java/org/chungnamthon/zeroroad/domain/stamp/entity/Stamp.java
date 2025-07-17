package org.chungnamthon.zeroroad.domain.stamp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.chungnamthon.zeroroad.global.entity.BaseTimeEntity;

@Table(name = "stamp")
@Entity
public class Stamp extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
