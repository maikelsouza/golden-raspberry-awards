package com.outsera.goldenraspberryawards.api.controller;

import com.outsera.goldenraspberryawards.api.dto.AwardIntervalDto;
import com.outsera.goldenraspberryawards.domain.service.ProductorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/producers")
@RequiredArgsConstructor
public class ProductorController {

    private final ProductorService service;

    @GetMapping("/award-interval")
    public ResponseEntity<AwardIntervalDto> findAwardInterval() {
        AwardIntervalDto awardInterval = service.findAwardInterval();
        return ResponseEntity.ok(awardInterval);
    }
}
