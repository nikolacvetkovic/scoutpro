package xyz.riocode.scoutpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.riocode.scoutpro.model.AppUserPlayer;
import xyz.riocode.scoutpro.model.AppUserPlayerId;

public interface AppUserPlayerRepository extends JpaRepository<AppUserPlayer, AppUserPlayerId> {
}
