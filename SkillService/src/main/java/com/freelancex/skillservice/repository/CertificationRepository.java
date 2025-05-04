package com.freelancex.skillservice.repository;

import com.freelancex.skillservice.model.Certification;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, UUID> {
}
