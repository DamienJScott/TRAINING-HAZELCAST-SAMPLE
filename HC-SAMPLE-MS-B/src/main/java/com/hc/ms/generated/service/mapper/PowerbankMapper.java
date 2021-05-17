package com.hc.ms.generated.service.mapper;


import com.hc.ms.generated.domain.*;
import com.hc.ms.generated.service.dto.PowerbankDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Powerbank} and its DTO {@link PowerbankDTO}.
 */
@Mapper(componentModel = "spring", uses = {StationMapper.class})
public interface PowerbankMapper extends EntityMapper<PowerbankDTO, Powerbank> {

    @Mapping(source = "station.id", target = "stationId")
    PowerbankDTO toDto(Powerbank powerbank);

    @Mapping(source = "stationId", target = "station")
    Powerbank toEntity(PowerbankDTO powerbankDTO);

    default Powerbank fromId(Long id) {
        if (id == null) {
            return null;
        }
        Powerbank powerbank = new Powerbank();
        powerbank.setId(id);
        return powerbank;
    }
}
