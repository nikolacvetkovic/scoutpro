package xyz.riocode.scoutpro.model.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "app_privilege")
public class AppPrivilege implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "privilege_name")
    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Set<AppRole> roles;
}
