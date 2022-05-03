package xyz.riocode.scoutpro.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.riocode.scoutpro.model.Player;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    @Query(value = "SELECT p FROM Player p " +
                " LEFT JOIN FETCH p.psmlTransfers " +
                " LEFT JOIN FETCH p.marketValues " +
                " LEFT JOIN FETCH p.competitionStatistics " +
                " JOIN FETCH p.users up " +
                " JOIN FETCH up.appUser u " +
                " WHERE u.username = :username",
            countQuery = "SELECT count(p) FROM Player p JOIN p.users up JOIN up.appUser u WHERE u.username = :username")
    Page<Player> findByUsername(String username, Pageable pageable);

    @Override
    @Query(value = "SELECT p FROM Player p " +
            " LEFT JOIN FETCH p.psmlTransfers " +
            " LEFT JOIN FETCH p.marketValues " +
            " LEFT JOIN FETCH p.competitionStatistics " +
            " LEFT JOIN FETCH p.gameStatistics " +
            " LEFT JOIN FETCH p.transfers ",
            countQuery = "SELECT count(p) FROM Player p")
    Page<Player> findAll(Pageable pageable);

    @Query(value = "SELECT p FROM Player p " +
            " LEFT JOIN FETCH p.psmlTransfers " +
            " LEFT JOIN FETCH p.marketValues " +
            " LEFT JOIN FETCH p.competitionStatistics " +
            " JOIN FETCH p.users up " +
            " JOIN FETCH up.appUser u " +
            " WHERE u.username = :username" +
            " AND p.primaryPosition = :position",
            countQuery =    "SELECT count(p) FROM Player p " +
                            "JOIN p.users up " +
                            "JOIN up.appUser u " +
                            "WHERE u.username = :username " +
                            "AND p.primaryPosition = :position")
    Page<Player> findByUsernameAndPosition(String username, String position, Pageable pageable);

    @Query("SELECT p FROM Player p " +
            "JOIN FETCH p.users up " +
            "JOIN FETCH up.appUser u " +
            "WHERE p.id = :id " +
            "AND u.username = :username")
    Optional<Player> findByIdAndUsername(Long id, String username);

    @Query("SELECT p FROM Player p " +
            "JOIN FETCH p.users up " +
            "JOIN FETCH up.appUser u " +
            "WHERE p.id = :id")
    Optional<Player> findById(Long id);

    @Query("SELECT p FROM Player p " +
            " LEFT JOIN FETCH p.psmlTransfers " +
            " LEFT JOIN FETCH p.transfers " +
            " LEFT JOIN FETCH p.marketValues " +
            " LEFT JOIN FETCH p.competitionStatistics " +
            " LEFT JOIN FETCH p.gameStatistics " +
            " JOIN FETCH p.users up " +
            " JOIN FETCH up.appUser u " +
            " WHERE p.id = :id" +
            " AND u.username = :username ")
    Optional<Player> findByIdAndUsernameComplete(Long id, String username);

    List<Player> findByNameContains(String playerName);

    @Query("SELECT DISTINCT p FROM Player p " +
            "LEFT JOIN FETCH p.marketValues " +
            "WHERE p NOT IN (SELECT p FROM p.users u" +
            "                JOIN u.appUser au" +
            "                WHERE au.username = :username)" +
            "AND p.name LIKE LOWER(CONCAT('%', :playerName, '%'))")
    List<Player> findByNameContainsAndWithoutUser(String playerName, String username);

    @Query("SELECT DISTINCT p FROM Player p " +
            "WHERE p IN (SELECT p FROM p.users u" +
            "                JOIN u.appUser au" +
            "                WHERE au.username = :username)" +
            "AND p.name LIKE LOWER(CONCAT('%', :playerName, '%'))")
    List<Player> findByNameContainsAndUser(String playerName, String username);

    Player findByTransfermarktCoreUrl(String transfermarktCoreUrl);

    @Query("SELECT p FROM Player p " +
            "WHERE p.pesDbPlayerName = :pesDbName")
    Player findByPesDbName(String pesDbName);

    @Query("SELECT p FROM Player p " +
            "LEFT JOIN FETCH p.psmlTransfers " +
            "WHERE p.psmlUrl = :psmlUrl")
    Player findByPsmlUrl(String psmlUrl);

    @Query(value = "SELECT p FROM Player p " +
            "LEFT JOIN FETCH p.transfers t " +
            "LEFT JOIN FETCH p.marketValues mv " +
            "WHERE p.transferLastCheck < :transferLastCheck",
            countQuery = "SELECT count(p) FROM Player p " +
                    "WHERE p.transferLastCheck < :transferLastCheck")
    Page<Player> findByTransferLastCheckBefore(LocalDateTime transferLastCheck, Pageable pageable);

    @Query(value = "SELECT count(p) FROM Player p " +
            "WHERE p.transferLastCheck < :transferLastCheck")
    long countByTransferLastCheckBefore(LocalDateTime transferLastCheck);

    @Query(value = "SELECT p FROM Player p " +
            "LEFT JOIN FETCH p.transfers t " +
            "LEFT JOIN FETCH p.marketValues mv " +
            "WHERE p.psmlLastCheck < :psmlLastCheck",
            countQuery = "SELECT count(p) FROM Player p " +
                    "WHERE p.psmlLastCheck < :psmlLastCheck")
    Page<Player> findByPsmlLastCheckBefore(LocalDateTime psmlLastCheck, Pageable pageable);

    @Query(value = "SELECT count(p) FROM Player p " +
            "WHERE p.psmlLastCheck < :psmlLastCheck")
    long countByPsmlLastCheckBefore(LocalDateTime psmlLastCheck);

    @Query(value = "SELECT p FROM Player p " +
            "LEFT JOIN FETCH p.transfers t " +
            "LEFT JOIN FETCH p.marketValues mv " +
            "WHERE p.pesDbLastCheck < :pesDbLastCheck",
            countQuery = "SELECT count(p) FROM Player p " +
                    "WHERE p.pesDbLastCheck < :pesDbLastCheck")
    Page<Player> findByPesDbLastCheckBefore(LocalDateTime pesDbLastCheck, Pageable pageable);

    @Query(value = "SELECT count(p) FROM Player p " +
            "WHERE p.pesDbLastCheck < :pesDbLastCheck")
    long countByPesDbLastCheckBefore(LocalDateTime pesDbLastCheck);

    @Query(value = "SELECT p FROM Player p " +
            "LEFT JOIN FETCH p.transfers t " +
            "LEFT JOIN FETCH p.marketValues mv " +
            "WHERE p.statisticLastCheck < :statisticLastCheck",
            countQuery = "SELECT count(p) FROM Player p " +
                    "WHERE p.statisticLastCheck < :statisticLastCheck")
    Page<Player> findByStatisticsLastCheckBefore(LocalDateTime statisticLastCheck, Pageable pageable);

    @Query(value = "SELECT count(p) FROM Player p " +
            "WHERE p.statisticLastCheck < :statisticLastCheck")
    long countByStatisticsLastCheckBefore(LocalDateTime statisticLastCheck);
}
