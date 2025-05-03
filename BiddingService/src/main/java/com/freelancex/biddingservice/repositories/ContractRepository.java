package com.freelancex.biddingservice.repositories;

import com.freelancex.biddingservice.models.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContractRepository extends JpaRepository<Contract, UUID> {

    List<Contract> findByBidFreelancerId(UUID freelancerId);

    List<Contract> findByJobClientId(UUID clientId);

    Optional<Contract> findByJobIdOrBidId(UUID jobId, UUID bidId);

    Optional<Contract> findByContractIdAndJobClientId(UUID contractId, UUID clientId);
}