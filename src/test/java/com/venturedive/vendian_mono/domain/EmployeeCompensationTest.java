package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeCompensationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeCompensation.class);
        EmployeeCompensation employeeCompensation1 = new EmployeeCompensation();
        employeeCompensation1.setId(1L);
        EmployeeCompensation employeeCompensation2 = new EmployeeCompensation();
        employeeCompensation2.setId(employeeCompensation1.getId());
        assertThat(employeeCompensation1).isEqualTo(employeeCompensation2);
        employeeCompensation2.setId(2L);
        assertThat(employeeCompensation1).isNotEqualTo(employeeCompensation2);
        employeeCompensation1.setId(null);
        assertThat(employeeCompensation1).isNotEqualTo(employeeCompensation2);
    }
}
