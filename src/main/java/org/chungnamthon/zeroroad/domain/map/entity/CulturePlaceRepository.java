package org.chungnamthon.zeroroad.domain.map.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CulturePlaceRepository extends JpaRepository<CulturePlace, Long> {

    List<CulturePlace> findByPlaceName(String keyword);
}
