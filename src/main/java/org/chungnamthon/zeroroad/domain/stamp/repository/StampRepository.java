package org.chungnamthon.zeroroad.domain.stamp.repository;

import org.chungnamthon.zeroroad.domain.stamp.entity.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StampRepository extends JpaRepository<Stamp, Long> {
}
