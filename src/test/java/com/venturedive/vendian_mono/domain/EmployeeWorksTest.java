package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeWorksTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeWorks.class);
        EmployeeWorks employeeWorks1 = new EmployeeWorks();
        employeeWorks1.setId(1L);
        EmployeeWorks employeeWorks2 = new EmployeeWorks();
        employeeWorks2.setId(employeeWorks1.getId());
        assertThat(employeeWorks1).isEqualTo(employeeWorks2);
        employeeWorks2.setId(2L);
        assertThat(employeeWorks1).isNotEqualTo(employeeWorks2);
        employeeWorks1.setId(null);
        assertThat(employeeWorks1).isNotEqualTo(employeeWorks2);
    }
}
