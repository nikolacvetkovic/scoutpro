package xyz.riocode.scoutpro.model.security;

import lombok.*;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "app_user")
public class AppUser implements Serializable, UserDetails, CredentialsContainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(min = 4, max = 50)
    @Column(name = "username")
    private String username;

    @Size(min = 4, max = 50)
    @Column(name = "pass")
    private String password;

    @Builder.Default
    @Column(name = "enabled")
    private boolean enabled = true;

    @Builder.Default
    @Column(name = "non_locked")
    private boolean nonLocked = true;

    @Builder.Default
    @Column(name = "non_expired")
    private boolean nonExpired = true;

    @Builder.Default
    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired = true;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appUser", orphanRemoval = true)
    private Set<AppUserPlayer> players = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable( name = "app_user_role",
                joinColumns = @JoinColumn(name = "app_user_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "app_role_id", referencedColumnName = "id"))
    private Set<AppRole> roles = new HashSet<>();

    public void removePlayer(AppUserPlayer appUserPlayer){
        this.players.remove(appUserPlayer);
        appUserPlayer.setAppUser(null);
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                            .map(AppRole::getPrivileges)
                .flatMap(Set::stream)
                .map(appPrivilege -> {
                    return new SimpleGrantedAuthority(appPrivilege.getName());
                })
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.nonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.nonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", username= " + username + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return Objects.equals(username, appUser.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}