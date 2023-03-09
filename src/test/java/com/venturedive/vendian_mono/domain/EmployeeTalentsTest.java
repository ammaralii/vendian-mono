package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeTalentsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeTalents.class);
        EmployeeTalents employeeTalents1 = new EmployeeTalents();
        employeeTalents1.setId(1L);
        EmployeeTalents employeeTalents2 = new EmployeeTalents();
        employeeTalents2.setId(employeeTalents1.getId());
        assertThat(employeeTalents1).isEqualTo(employeeTalents2);
        employeeTalents2.setId(2L);
        assertThat(employeeTalents1).isNotEqualTo(employeeTalents2);
        employeeTalents1.setId(null);
        assertThat(employeeTalents1).isNotEqualTo(employeeTalents2);
    }
}
