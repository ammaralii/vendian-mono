package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeJobInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeJobInfo.class);
        EmployeeJobInfo employeeJobInfo1 = new EmployeeJobInfo();
        employeeJobInfo1.setId(1L);
        EmployeeJobInfo employeeJobInfo2 = new EmployeeJobInfo();
        employeeJobInfo2.setId(employeeJobInfo1.getId());
        assertThat(employeeJobInfo1).isEqualTo(employeeJobInfo2);
        employeeJobInfo2.setId(2L);
        assertThat(employeeJobInfo1).isNotEqualTo(employeeJobInfo2);
        employeeJobInfo1.setId(null);
        assertThat(employeeJobInfo1).isNotEqualTo(employeeJobInfo2);
    }
}
