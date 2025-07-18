package org.chungnamthon.zeroroad.domain.profilestore.respository;


import org.chungnamthon.zeroroad.domain.member.entity.Member;
import org.chungnamthon.zeroroad.domain.profilestore.entity.MemberOwnedProfileItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MemberOwnedProfileItemRepository extends JpaRepository<MemberOwnedProfileItem, Long> {
    List<MemberOwnedProfileItem> findByMember(Member member);
}