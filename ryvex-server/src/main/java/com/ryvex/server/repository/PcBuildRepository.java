package com.ryvex.server.repository;

import com.ryvex.server.model.PcBuild;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PcBuildRepository
        extends JpaRepository<PcBuild, Long> {

    List<PcBuild> findAllByUser_IdOrderByUpdatedAtDesc(
            Long userId
    );

    Optional<PcBuild> findByIdAndUser_Id(
            Long id,
            Long userId
    );

    long countByUser_Id(
            Long userId
    );

    boolean existsByIdAndUser_Id(
            Long id,
            Long userId
    );
}