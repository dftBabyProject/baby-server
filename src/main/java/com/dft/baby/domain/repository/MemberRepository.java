package com.dft.baby.domain.repository;

import com.dft.baby.domain.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m " +
            "left join fetch m.auth " +
            "where m.id = :id")
    Optional<Member> findById(Long id);

    @Query("select m from Member m " +
            "left join fetch m.roles " +
            "where m.socialId = :socialId")
    Optional<Member> findBySocialId(String socialId);
}
