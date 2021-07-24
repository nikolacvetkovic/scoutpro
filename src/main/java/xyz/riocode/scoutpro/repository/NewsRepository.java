package xyz.riocode.scoutpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.riocode.scoutpro.model.News;

public interface NewsRepository extends JpaRepository<News, Long> {
}
