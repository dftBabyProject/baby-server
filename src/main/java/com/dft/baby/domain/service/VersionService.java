package com.dft.baby.domain.service;

import com.dft.baby.domain.dto.version.VersionDto;
import com.dft.baby.domain.entity.member.VersionHistory;
import com.dft.baby.domain.repository.VersionHistoryRepository;
import com.dft.baby.web.exception.CommonException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.dft.baby.web.exception.ExceptionType.VERSION_INVALID;
import static com.dft.baby.web.util.Util.ANDROID;
import static com.dft.baby.web.util.Util.IOS;

@Service
@RequiredArgsConstructor
@Transactional
public class VersionService {

    private final VersionHistoryRepository versionHistoryRepository;
    private String androidVersionCache = null;
    private String iosVersionCache = null;

    @PostConstruct
    public void initializeCache() {
        List<VersionHistory> allVersions = versionHistoryRepository.findAll();

        for (VersionHistory versionHistory : allVersions) {
            updateCache(versionHistory.getId(), versionHistory.getVersion());
        }
    }

    public void createVersion(VersionDto dto) {
        String VERSION_REGEX = "\\d+\\.\\d+\\.\\d+";

        if (!Objects.equals(dto.getOs(), ANDROID) && !Objects.equals(dto.getOs(), IOS)) {
            throw new CommonException(VERSION_INVALID.getCode(), VERSION_INVALID.getErrorMessage());
        }

        if (dto.getVersion() == null || dto.getVersion().isEmpty()) {
            throw new CommonException(VERSION_INVALID.getCode(), VERSION_INVALID.getErrorMessage());
        }

        if (!dto.getVersion().matches(VERSION_REGEX)) {
            throw new CommonException(VERSION_INVALID.getCode(), VERSION_INVALID.getErrorMessage());
        }

        Optional<VersionHistory> findVersion = versionHistoryRepository.findById(dto.getOs());

        if (findVersion.isEmpty()) {
            versionHistoryRepository.save(new VersionHistory(dto));
            updateCache(dto.getOs(), dto.getVersion());
            return;
        }

        VersionHistory versionHistory = findVersion.get();
        if (isLatestVersionHigher(dto.getVersion(), versionHistory.getVersion())) {
            versionHistory.setVersion(dto.getVersion());
            updateCache(dto.getOs(), dto.getVersion());
        } else {
            throw new CommonException(VERSION_INVALID.getCode(), VERSION_INVALID.getErrorMessage());
        }
    }

    public String getAndroidVersionCache() {
        if (androidVersionCache != null) {
            return androidVersionCache;
        }

        Optional<VersionHistory> findVersion = versionHistoryRepository.findById(ANDROID);
        return findVersion.map(VersionHistory::getVersion).orElse(null);
    }

    public String getIosVersionCache() {
        if (iosVersionCache != null) {
            return iosVersionCache;
        }

        Optional<VersionHistory> findVersion = versionHistoryRepository.findById(IOS);
        return findVersion.map(VersionHistory::getVersion).orElse(null);
    }

    private void updateCache(Long os, String version) {
        if (os.equals(ANDROID)) {
            androidVersionCache = version;
            System.out.println(androidVersionCache);
        } else if (os.equals(IOS)) {
            iosVersionCache = version;
            System.out.println(iosVersionCache);
        }
    }

    private boolean isLatestVersionHigher(String latest, String current) {
        String[] latestParts = latest.split("\\.");
        String[] currentParts = current.split("\\.");

        for (int i = 0; i < 3; i++) {
            int latestPart = Integer.parseInt(latestParts[i]);
            int currentPart = Integer.parseInt(currentParts[i]);

            if (latestPart > currentPart) return true;
            if (latestPart < currentPart) return false;
        }

        return false;
    }

    public void cleanup() {
        androidVersionCache = null;
        iosVersionCache = null;
    }
}
