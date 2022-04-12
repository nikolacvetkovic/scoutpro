package xyz.riocode.scoutpro.model.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "app_role")
public class AppRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 10)
    @Column(name = "role_name")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<AppUser> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name = "app_role_privilege",
                joinColumns = @JoinColumn(name = "app_role_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "app_privilege_id", referencedColumnName = "id"))
    private Set<AppPrivilege> privileges;
}
