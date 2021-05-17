package com.hc.generated.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hc.generated.web.rest.TestUtil;

public class PowerbankTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Powerbank.class);
        Powerbank powerbank1 = new Powerbank();
        powerbank1.setId(1L);
        Powerbank powerbank2 = new Powerbank();
        powerbank2.setId(powerbank1.getId());
        assertThat(powerbank1).isEqualTo(powerbank2);
        powerbank2.setId(2L);
        assertThat(powerbank1).isNotEqualTo(powerbank2);
        powerbank1.setId(null);
        assertThat(powerbank1).isNotEqualTo(powerbank2);
    }
}
