package com.dft.baby.domain.dto.version;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class VersionResponseDto {

    private String androidVersion;
    private String iosVersion;

    public VersionResponseDto(String androidVersion, String iosVersion) {
        this.androidVersion = androidVersion;
        this.iosVersion = iosVersion;
    }
}
