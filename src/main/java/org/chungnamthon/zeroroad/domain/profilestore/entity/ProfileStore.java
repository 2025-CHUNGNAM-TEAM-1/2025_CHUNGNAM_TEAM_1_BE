package org.chungnamthon.zeroroad.domain.profilestore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ProfileStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
