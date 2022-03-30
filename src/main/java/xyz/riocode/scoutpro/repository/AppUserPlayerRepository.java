package xyz.riocode.scoutpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.riocode.scoutpro.model.security.AppUserPlayer;
import xyz.riocode.scoutpro.model.security.AppUserPlayerId;

public interface AppUserPlayerRepository extends JpaRepository<AppUserPlayer, AppUserPlayerId> {
}
