package xyz.riocode.scoutpro.service;

import org.springframework.stereotype.Component;
import xyz.riocode.scoutpro.model.News;
import xyz.riocode.scoutpro.model.Player;

import java.util.List;

@Component
public class NewsServiceImpl implements NewsService {
    @Override
    public News save(News news) {
        return null;
    }

    @Override
    public List<News> getByPlayer(Player player) {
        return null;
    }
}
