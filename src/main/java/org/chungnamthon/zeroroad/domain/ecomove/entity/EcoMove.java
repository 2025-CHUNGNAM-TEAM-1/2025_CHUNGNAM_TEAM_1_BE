package org.chungnamthon.zeroroad.domain.ecomove.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class EcoMove {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}