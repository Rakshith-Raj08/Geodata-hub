package com.rakshith.geodatahub.repository;

import com.rakshith.geodatahub.entity.Dataset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DatasetRepository extends JpaRepository<Dataset, Long> {
    Optional<Dataset> findBySlug(String slug);
}
