package com.hc.generated.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StationMapperTest {

    private StationMapper stationMapper;

    @BeforeEach
    public void setUp() {
        stationMapper = new StationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(stationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(stationMapper.fromId(null)).isNull();
    }
}
