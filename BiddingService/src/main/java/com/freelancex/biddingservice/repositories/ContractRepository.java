package com.freelancex.biddingservice.repositories;

import com.freelancex.biddingservice.models.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContractRepository extends JpaRepository<Contract, UUID> {

    List<Contract> findByBidUserId(UUID userId);

    Optional<Contract> findByContractIdAndBidUserId(UUID contractId, UUID bidUserId);

    Optional<Contract> findContractByContractIdAndJobUserId(UUID contractId, UUID clientId);

    List<Contract> findContractByJobUserId(UUID clientId);

    boolean existsContractByBidId(UUID bidId);

    boolean existsContractByBidIdOrJobId(UUID bidId, UUID jobId);
}