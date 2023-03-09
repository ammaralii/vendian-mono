package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeRolesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeRoles.class);
        EmployeeRoles employeeRoles1 = new EmployeeRoles();
        employeeRoles1.setId(1L);
        EmployeeRoles employeeRoles2 = new EmployeeRoles();
        employeeRoles2.setId(employeeRoles1.getId());
        assertThat(employeeRoles1).isEqualTo(employeeRoles2);
        employeeRoles2.setId(2L);
        assertThat(employeeRoles1).isNotEqualTo(employeeRoles2);
        employeeRoles1.setId(null);
        assertThat(employeeRoles1).isNotEqualTo(employeeRoles2);
    }
}
