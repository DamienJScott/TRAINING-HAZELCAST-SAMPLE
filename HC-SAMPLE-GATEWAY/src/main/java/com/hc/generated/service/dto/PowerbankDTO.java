package com.hc.generated.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.hc.generated.domain.Powerbank} entity.
 */
public class PowerbankDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private String desc;

    @NotNull
    private String type;

    @NotNull
    private String position;

    @NotNull
    private Boolean isUsing;

    @NotNull
    private Boolean enabled;


    private Long stationId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Boolean isIsUsing() {
        return isUsing;
    }

    public void setIsUsing(Boolean isUsing) {
        this.isUsing = isUsing;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PowerbankDTO)) {
            return false;
        }

        return id != null && id.equals(((PowerbankDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PowerbankDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", desc='" + getDesc() + "'" +
            ", type='" + getType() + "'" +
            ", position='" + getPosition() + "'" +
            ", isUsing='" + isIsUsing() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", stationId=" + getStationId() +
            "}";
    }
}
