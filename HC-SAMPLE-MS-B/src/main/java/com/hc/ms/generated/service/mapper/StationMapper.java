package com.hc.ms.generated.service.mapper;


import com.hc.ms.generated.domain.*;
import com.hc.ms.generated.service.dto.StationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Station} and its DTO {@link StationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StationMapper extends EntityMapper<StationDTO, Station> {


    @Mapping(target = "powerbanks", ignore = true)
    @Mapping(target = "removePowerbank", ignore = true)
    Station toEntity(StationDTO stationDTO);

    default Station fromId(Long id) {
        if (id == null) {
            return null;
        }
        Station station = new Station();
        station.setId(id);
        return station;
    }
}
