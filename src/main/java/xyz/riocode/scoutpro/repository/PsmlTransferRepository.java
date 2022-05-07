package xyz.riocode.scoutpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.riocode.scoutpro.model.PsmlTransfer;

import java.time.LocalDate;
import java.util.List;

public interface PsmlTransferRepository extends JpaRepository<PsmlTransfer, Long> {
    @Query(value = "SELECT pt FROM PsmlTransfer pt " +
            " JOIN pt.player pl " +
            " WHERE pt.dateOfTransfer > :dateOfTransfer" +
            " AND pl IN (SELECT pl FROM pl.users u " +
            "            JOIN u.appUser au " +
            "            WHERE au.username = :username)")
    List<PsmlTransfer> findByDateOfTransferAndUser(LocalDate dateOfTransfer, String username);
}
