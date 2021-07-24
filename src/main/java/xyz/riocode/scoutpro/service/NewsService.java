package xyz.riocode.scoutpro.service;

import xyz.riocode.scoutpro.model.News;
import xyz.riocode.scoutpro.model.Player;

import java.util.List;

public interface NewsService {
    News save(News news);
    List<News> getByPlayer(Player player);
}
