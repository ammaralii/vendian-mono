package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeEducationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeEducation.class);
        EmployeeEducation employeeEducation1 = new EmployeeEducation();
        employeeEducation1.setId(1L);
        EmployeeEducation employeeEducation2 = new EmployeeEducation();
        employeeEducation2.setId(employeeEducation1.getId());
        assertThat(employeeEducation1).isEqualTo(employeeEducation2);
        employeeEducation2.setId(2L);
        assertThat(employeeEducation1).isNotEqualTo(employeeEducation2);
        employeeEducation1.setId(null);
        assertThat(employeeEducation1).isNotEqualTo(employeeEducation2);
    }
}
