package com.hc.generated.service.mapper;


import com.hc.generated.domain.*;
import com.hc.generated.service.dto.AlbumDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Album} and its DTO {@link AlbumDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AlbumMapper extends EntityMapper<AlbumDTO, Album> {


    @Mapping(target = "photos", ignore = true)
    @Mapping(target = "removePhoto", ignore = true)
    Album toEntity(AlbumDTO albumDTO);

    default Album fromId(Long id) {
        if (id == null) {
            return null;
        }
        Album album = new Album();
        album.setId(id);
        return album;
    }
}
