package xyz.riocode.scoutpro.scrape.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.riocode.scoutpro.scrape.enums.ScrapeField;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "scrape_reg_expression")
public class ScrapeRegExpression implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name= "field_name")
    @Enumerated(EnumType.STRING)
    private ScrapeField fieldName;

    @Basic(optional = false)
    @Column(name = "regex")
    private String regex;

}
