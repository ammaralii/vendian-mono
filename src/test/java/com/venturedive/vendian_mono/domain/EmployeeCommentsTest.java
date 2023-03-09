package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeCommentsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeComments.class);
        EmployeeComments employeeComments1 = new EmployeeComments();
        employeeComments1.setId(1L);
        EmployeeComments employeeComments2 = new EmployeeComments();
        employeeComments2.setId(employeeComments1.getId());
        assertThat(employeeComments1).isEqualTo(employeeComments2);
        employeeComments2.setId(2L);
        assertThat(employeeComments1).isNotEqualTo(employeeComments2);
        employeeComments1.setId(null);
        assertThat(employeeComments1).isNotEqualTo(employeeComments2);
    }
}
