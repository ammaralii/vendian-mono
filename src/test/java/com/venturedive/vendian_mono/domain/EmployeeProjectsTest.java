package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeProjectsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeProjects.class);
        EmployeeProjects employeeProjects1 = new EmployeeProjects();
        employeeProjects1.setId(1L);
        EmployeeProjects employeeProjects2 = new EmployeeProjects();
        employeeProjects2.setId(employeeProjects1.getId());
        assertThat(employeeProjects1).isEqualTo(employeeProjects2);
        employeeProjects2.setId(2L);
        assertThat(employeeProjects1).isNotEqualTo(employeeProjects2);
        employeeProjects1.setId(null);
        assertThat(employeeProjects1).isNotEqualTo(employeeProjects2);
    }
}
