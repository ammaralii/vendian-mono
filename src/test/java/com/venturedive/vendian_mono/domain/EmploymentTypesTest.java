package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmploymentTypesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmploymentTypes.class);
        EmploymentTypes employmentTypes1 = new EmploymentTypes();
        employmentTypes1.setId(1L);
        EmploymentTypes employmentTypes2 = new EmploymentTypes();
        employmentTypes2.setId(employmentTypes1.getId());
        assertThat(employmentTypes1).isEqualTo(employmentTypes2);
        employmentTypes2.setId(2L);
        assertThat(employmentTypes1).isNotEqualTo(employmentTypes2);
        employmentTypes1.setId(null);
        assertThat(employmentTypes1).isNotEqualTo(employmentTypes2);
    }
}
