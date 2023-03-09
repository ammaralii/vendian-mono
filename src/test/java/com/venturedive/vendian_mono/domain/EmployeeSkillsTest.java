package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeSkillsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeSkills.class);
        EmployeeSkills employeeSkills1 = new EmployeeSkills();
        employeeSkills1.setId(1L);
        EmployeeSkills employeeSkills2 = new EmployeeSkills();
        employeeSkills2.setId(employeeSkills1.getId());
        assertThat(employeeSkills1).isEqualTo(employeeSkills2);
        employeeSkills2.setId(2L);
        assertThat(employeeSkills1).isNotEqualTo(employeeSkills2);
        employeeSkills1.setId(null);
        assertThat(employeeSkills1).isNotEqualTo(employeeSkills2);
    }
}
