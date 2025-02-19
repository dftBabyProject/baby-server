package com.dft.baby.web.controller;

import com.dft.baby.domain.dto.version.VersionResponseDto;
import com.dft.baby.domain.service.VersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/common")
@RequiredArgsConstructor
public class CommonController {

    private final VersionService versionService;

    @GetMapping("/version-check")
    public VersionResponseDto getVersion() {
        String androidVersionCache = versionService.getAndroidVersionCache();
        String iosVersionCache = versionService.getIosVersionCache();
        return new VersionResponseDto(androidVersionCache, iosVersionCache);
    }
}
