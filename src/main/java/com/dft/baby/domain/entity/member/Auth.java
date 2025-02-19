package com.dft.baby.domain.entity.member;

import com.dft.baby.domain.entity.base.BaseEntity;
import com.dft.baby.web.security.EncryptorConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "auth", indexes = {
        @Index(name = "idx_phone_number", columnList = "phone_number"),
        @Index(name = "idx_delete_date", columnList = "deleteDate"),
        @Index(name = "idx_auth_is_delete", columnList = "isDelete")
})
public class Auth extends BaseEntity {

    @Id
    @GeneratedValue(generator = "auth_seq_id")
    @GenericGenerator(name = "auth_seq_id", strategy = "com.dft.baby.domain.generator.AuthIDGenerator")
    @Column(name = "authId")
    private Long id;

    private LocalDateTime lastChanged;

    @Column(name = "deleteDate")
    private LocalDate deleteDate;

    @Column(name = "isDelete", nullable = false)
    private boolean isDelete = false;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "auth")
    private Member member;

    public Auth(Member member) {
        this.member = member;
        member.setAuth(this);
    }
}
