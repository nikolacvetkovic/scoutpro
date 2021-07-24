package xyz.riocode.scoutpro.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class AppUserPlayerId implements Serializable {

    public AppUserPlayerId(Long playerId, Long userId){
        this.playerId = playerId;
        this.userId = userId;
    }

    @Column(name = "app_user_id")
    private Long userId;
    @Column(name = "player_id")
    private Long playerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        AppUserPlayerId that = (AppUserPlayerId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(playerId, that.playerId);
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (playerId != null ? playerId.hashCode() : 0);
        return result;
    }
}
