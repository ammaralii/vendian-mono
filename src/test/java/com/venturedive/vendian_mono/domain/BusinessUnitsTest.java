package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BusinessUnitsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessUnits.class);
        BusinessUnits businessUnits1 = new BusinessUnits();
        businessUnits1.setId(1L);
        BusinessUnits businessUnits2 = new BusinessUnits();
        businessUnits2.setId(businessUnits1.getId());
        assertThat(businessUnits1).isEqualTo(businessUnits2);
        businessUnits2.setId(2L);
        assertThat(businessUnits1).isNotEqualTo(businessUnits2);
        businessUnits1.setId(null);
        assertThat(businessUnits1).isNotEqualTo(businessUnits2);
    }
}
