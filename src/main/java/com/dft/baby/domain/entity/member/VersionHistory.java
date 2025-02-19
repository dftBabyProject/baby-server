package com.dft.baby.domain.entity.member;

import com.dft.baby.domain.dto.version.VersionDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VersionHistory {

    @Id
    @Column(name = "versionHistoryId")
    private Long id;

    private String version;

    public VersionHistory(VersionDto dto) {
        id = dto.getOs();
        version = dto.getVersion();
    }
}
