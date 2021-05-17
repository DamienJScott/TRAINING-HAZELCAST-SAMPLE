package com.hc.ms.generated.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Powerbank.
 */
@Entity
@Table(name = "powerbank")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Powerbank implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "hc_desc")
    private String desc;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @NotNull
    @Column(name = "position", nullable = false)
    private String position;

    @NotNull
    @Column(name = "is_using", nullable = false)
    private Boolean isUsing;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @ManyToOne
    @JsonIgnoreProperties(value = "powerbanks", allowSetters = true)
    private Station station;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Powerbank name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public Powerbank desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public Powerbank type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPosition() {
        return position;
    }

    public Powerbank position(String position) {
        this.position = position;
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Boolean isIsUsing() {
        return isUsing;
    }

    public Powerbank isUsing(Boolean isUsing) {
        this.isUsing = isUsing;
        return this;
    }

    public void setIsUsing(Boolean isUsing) {
        this.isUsing = isUsing;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Powerbank enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Station getStation() {
        return station;
    }

    public Powerbank station(Station station) {
        this.station = station;
        return this;
    }

    public void setStation(Station station) {
        this.station = station;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Powerbank)) {
            return false;
        }
        return id != null && id.equals(((Powerbank) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Powerbank{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", desc='" + getDesc() + "'" +
            ", type='" + getType() + "'" +
            ", position='" + getPosition() + "'" +
            ", isUsing='" + isIsUsing() + "'" +
            ", enabled='" + isEnabled() + "'" +
            "}";
    }
}
