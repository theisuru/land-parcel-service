package com.example.landparcelservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LandParcelRepository extends JpaRepository<LandParcel, Long> {
}
