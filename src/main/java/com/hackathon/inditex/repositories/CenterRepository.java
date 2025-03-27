package com.hackathon.inditex.repositories;

import com.hackathon.inditex.entities.Center;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Center Repository.
 */
@Repository
public interface CenterRepository extends JpaRepository<Center, Long> {
    Optional<Center> findByCoordinatesLatitudeAndCoordinatesLongitude(Double latitude, Double longitude);
}
