package com.hc.generated.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PowerbankMapperTest {

    private PowerbankMapper powerbankMapper;

    @BeforeEach
    public void setUp() {
        powerbankMapper = new PowerbankMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(powerbankMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(powerbankMapper.fromId(null)).isNull();
    }
}
