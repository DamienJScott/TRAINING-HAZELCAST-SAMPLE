package com.hc.generated.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hc.generated.web.rest.TestUtil;

public class PowerbankDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PowerbankDTO.class);
        PowerbankDTO powerbankDTO1 = new PowerbankDTO();
        powerbankDTO1.setId(1L);
        PowerbankDTO powerbankDTO2 = new PowerbankDTO();
        assertThat(powerbankDTO1).isNotEqualTo(powerbankDTO2);
        powerbankDTO2.setId(powerbankDTO1.getId());
        assertThat(powerbankDTO1).isEqualTo(powerbankDTO2);
        powerbankDTO2.setId(2L);
        assertThat(powerbankDTO1).isNotEqualTo(powerbankDTO2);
        powerbankDTO1.setId(null);
        assertThat(powerbankDTO1).isNotEqualTo(powerbankDTO2);
    }
}
