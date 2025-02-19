package com.dft.baby.domain.entity.member;

import com.dft.baby.domain.dto.member.req.MemberAppleCreateRequestDto;
import com.dft.baby.domain.dto.member.req.MemberCreateRequestDto;
import com.dft.baby.domain.entity.base.BaseEntity;
import com.dft.baby.web.security.EncryptorConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

import static com.dft.baby.web.util.Util.DEFAULT_PROFILE_IMAGE;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(generator = "member_seq_id")
    @GenericGenerator(name = "member_seq_id", strategy = "com.dft.baby.domain.generator.MemberIDGenerator")
    @Column(name = "memberId")
    private Long id;

    private String nickname;
    private String socialId;
    private String socialType;

    @Convert(converter = EncryptorConverter.class)
    private String profileImage;

    @Column(unique = true)
    private String code;
    private String device;
    private Integer alarmList;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> roles = new ArrayList<>();

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "authId")
    private Auth auth;

    public Member(MemberCreateRequestDto dto, String coupleCode, String socialId) {
        this.socialId = socialId;
        this.socialType = "KAKAO";
        this.nickname = dto.getNickname();
        this.code = coupleCode;
        this.profileImage = DEFAULT_PROFILE_IMAGE;
        this.alarmList = 127;
        this.getRoles().add("USER");
    }

    public Member(MemberAppleCreateRequestDto dto, String coupleCode, String socialId) {
        this.socialId = socialId;
        this.socialType = "APPLE";
        this.nickname = dto.getNickname();
        this.code = coupleCode;
        this.profileImage = DEFAULT_PROFILE_IMAGE;
        this.alarmList = 127;
        this.getRoles().add("USER");
    }

    public void deleteMember() {
        this.socialId = null;
        this.socialType = null;
        this.nickname = null;
        this.profileImage = "";
        this.device = null;
    }
}
