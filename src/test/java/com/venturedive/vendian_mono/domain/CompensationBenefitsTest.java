package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompensationBenefitsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompensationBenefits.class);
        CompensationBenefits compensationBenefits1 = new CompensationBenefits();
        compensationBenefits1.setId(1L);
        CompensationBenefits compensationBenefits2 = new CompensationBenefits();
        compensationBenefits2.setId(compensationBenefits1.getId());
        assertThat(compensationBenefits1).isEqualTo(compensationBenefits2);
        compensationBenefits2.setId(2L);
        assertThat(compensationBenefits1).isNotEqualTo(compensationBenefits2);
        compensationBenefits1.setId(null);
        assertThat(compensationBenefits1).isNotEqualTo(compensationBenefits2);
    }
}
