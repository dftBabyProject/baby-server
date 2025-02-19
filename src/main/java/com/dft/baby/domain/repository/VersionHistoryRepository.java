package com.dft.baby.domain.repository;

import com.dft.baby.domain.entity.member.VersionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VersionHistoryRepository extends JpaRepository<VersionHistory, Long> {

    @Query("SELECT vh FROM VersionHistory vh WHERE vh.id = :id")
    Optional<VersionHistory> findById(Long id);
}
