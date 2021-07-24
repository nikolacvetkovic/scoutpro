package xyz.riocode.scoutpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.riocode.scoutpro.model.AppUser;

import java.util.Optional;
import java.util.Set;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Set<AppUser> findAppUsersByUsername(String username);

    @Query("SELECT u FROM AppUser u LEFT JOIN FETCH u.players WHERE u.username = :username")
    Optional<AppUser> findByUsername(String username);
}
