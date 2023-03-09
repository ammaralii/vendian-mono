package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeProjectRatingsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeProjectRatings.class);
        EmployeeProjectRatings employeeProjectRatings1 = new EmployeeProjectRatings();
        employeeProjectRatings1.setId(1L);
        EmployeeProjectRatings employeeProjectRatings2 = new EmployeeProjectRatings();
        employeeProjectRatings2.setId(employeeProjectRatings1.getId());
        assertThat(employeeProjectRatings1).isEqualTo(employeeProjectRatings2);
        employeeProjectRatings2.setId(2L);
        assertThat(employeeProjectRatings1).isNotEqualTo(employeeProjectRatings2);
        employeeProjectRatings1.setId(null);
        assertThat(employeeProjectRatings1).isNotEqualTo(employeeProjectRatings2);
    }
}
