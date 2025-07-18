package org.chungnamthon.zeroroad.domain.profilestore.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store")
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/profiles")
    public List<ProfileItemDto> getProfileItems() {
        return storeService.getAllProfileItems();
    }
}
