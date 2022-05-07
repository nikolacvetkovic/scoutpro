package xyz.riocode.scoutpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.riocode.scoutpro.model.Transfer;

import java.time.LocalDate;
import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
    @Query(value = "SELECT t FROM Transfer t " +
                    " JOIN t.player pl " +
                    " WHERE t.dateOfTransfer > :dateOfTransfer" +
                    " AND pl IN (SELECT pl FROM pl.users u " +
                    "            JOIN u.appUser au " +
                    "            WHERE au.username = :username)")
    List<Transfer> findByDateOfTransferAndUser(LocalDate dateOfTransfer, String username);
}
