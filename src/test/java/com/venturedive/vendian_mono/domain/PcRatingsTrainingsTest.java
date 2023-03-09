package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PcRatingsTrainingsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PcRatingsTrainings.class);
        PcRatingsTrainings pcRatingsTrainings1 = new PcRatingsTrainings();
        pcRatingsTrainings1.setId(1L);
        PcRatingsTrainings pcRatingsTrainings2 = new PcRatingsTrainings();
        pcRatingsTrainings2.setId(pcRatingsTrainings1.getId());
        assertThat(pcRatingsTrainings1).isEqualTo(pcRatingsTrainings2);
        pcRatingsTrainings2.setId(2L);
        assertThat(pcRatingsTrainings1).isNotEqualTo(pcRatingsTrainings2);
        pcRatingsTrainings1.setId(null);
        assertThat(pcRatingsTrainings1).isNotEqualTo(pcRatingsTrainings2);
    }
}
