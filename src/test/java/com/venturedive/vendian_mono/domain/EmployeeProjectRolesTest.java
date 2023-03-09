package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeProjectRolesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeProjectRoles.class);
        EmployeeProjectRoles employeeProjectRoles1 = new EmployeeProjectRoles();
        employeeProjectRoles1.setId(1L);
        EmployeeProjectRoles employeeProjectRoles2 = new EmployeeProjectRoles();
        employeeProjectRoles2.setId(employeeProjectRoles1.getId());
        assertThat(employeeProjectRoles1).isEqualTo(employeeProjectRoles2);
        employeeProjectRoles2.setId(2L);
        assertThat(employeeProjectRoles1).isNotEqualTo(employeeProjectRoles2);
        employeeProjectRoles1.setId(null);
        assertThat(employeeProjectRoles1).isNotEqualTo(employeeProjectRoles2);
    }
}
