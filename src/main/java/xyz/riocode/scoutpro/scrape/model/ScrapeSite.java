package xyz.riocode.scoutpro.scrape.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.riocode.scoutpro.scrape.enums.ScrapeSiteStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "scrape_site")
public class ScrapeSite implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "hostname")
    private String hostname;

    @Column(name = "loader_name")
    private String loaderName;

    @Column(name = "template_name")
    private String templateName;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ScrapeSiteStatus status;

    @Column(name = "last_checked")
    private LocalDateTime lastChecked;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "scrapeSite", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ScrapeField> scrapeFields = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScrapeSite that = (ScrapeSite) o;

        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
