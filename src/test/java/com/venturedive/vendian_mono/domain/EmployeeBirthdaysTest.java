package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeBirthdaysTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeBirthdays.class);
        EmployeeBirthdays employeeBirthdays1 = new EmployeeBirthdays();
        employeeBirthdays1.setId(1L);
        EmployeeBirthdays employeeBirthdays2 = new EmployeeBirthdays();
        employeeBirthdays2.setId(employeeBirthdays1.getId());
        assertThat(employeeBirthdays1).isEqualTo(employeeBirthdays2);
        employeeBirthdays2.setId(2L);
        assertThat(employeeBirthdays1).isNotEqualTo(employeeBirthdays2);
        employeeBirthdays1.setId(null);
        assertThat(employeeBirthdays1).isNotEqualTo(employeeBirthdays2);
    }
}
