package xyz.riocode.scoutpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.riocode.scoutpro.model.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
