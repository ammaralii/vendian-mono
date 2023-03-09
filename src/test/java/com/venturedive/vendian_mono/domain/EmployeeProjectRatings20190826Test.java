package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeProjectRatings20190826Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeProjectRatings20190826.class);
        EmployeeProjectRatings20190826 employeeProjectRatings201908261 = new EmployeeProjectRatings20190826();
        employeeProjectRatings201908261.setId(1L);
        EmployeeProjectRatings20190826 employeeProjectRatings201908262 = new EmployeeProjectRatings20190826();
        employeeProjectRatings201908262.setId(employeeProjectRatings201908261.getId());
        assertThat(employeeProjectRatings201908261).isEqualTo(employeeProjectRatings201908262);
        employeeProjectRatings201908262.setId(2L);
        assertThat(employeeProjectRatings201908261).isNotEqualTo(employeeProjectRatings201908262);
        employeeProjectRatings201908261.setId(null);
        assertThat(employeeProjectRatings201908261).isNotEqualTo(employeeProjectRatings201908262);
    }
}
