package org.chungnamthon.zeroroad.domain.profilestore.respository;

import org.chungnamthon.zeroroad.domain.profilestore.entity.ProfileStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileStoreRepository extends JpaRepository<ProfileStore, String> {
}
