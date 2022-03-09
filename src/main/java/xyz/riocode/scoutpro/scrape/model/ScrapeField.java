package xyz.riocode.scoutpro.scrape.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "scrape_field")
public class ScrapeField implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name= "name")
    private String name;

    @Column(name = "selector")
    private String selector;

    @ManyToOne
    @JoinColumn(name = "scrape_site_id", referencedColumnName = "id")
    private ScrapeSite scrapeSite;

}
