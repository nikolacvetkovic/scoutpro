package xyz.riocode.scoutpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.riocode.scoutpro.model.MarketValue;

public interface MarketValueRepository extends JpaRepository<MarketValue, Long> {
}
