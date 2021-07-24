package xyz.riocode.scoutpro.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "app_user_player")
public class AppUserPlayer implements Serializable {

    public AppUserPlayer(Player player, AppUser appUser){
        this.player = player;
        this.appUser = appUser;
        this.appUserPlayerId = new AppUserPlayerId(player.getId(), appUser.getId());
    }

    @EmbeddedId
    private AppUserPlayerId appUserPlayerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("playerId")
    private Player player;

    @Column(name = "my_player")
    private boolean myPlayer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        AppUserPlayer that = (AppUserPlayer) o;
        return Objects.equals(appUser, that.appUser) &&
                Objects.equals(player, that.player);
    }
}
