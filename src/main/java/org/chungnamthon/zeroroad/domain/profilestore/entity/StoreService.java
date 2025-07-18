package org.chungnamthon.zeroroad.domain.profilestore.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final ProfileStoreRepository profileStoreRepository;

    @Transactional(readOnly = true)
    public List<ProfileItemDto> getAllProfileItems() {
        List<ProfileStore> allItems = profileStoreRepository.findAll();

        return allItems.stream()
                .map(ProfileItemDto::new)
                .collect(Collectors.toList());
    }
}
