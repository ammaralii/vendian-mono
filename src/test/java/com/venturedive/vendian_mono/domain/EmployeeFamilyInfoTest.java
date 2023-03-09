package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeFamilyInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeFamilyInfo.class);
        EmployeeFamilyInfo employeeFamilyInfo1 = new EmployeeFamilyInfo();
        employeeFamilyInfo1.setId(1L);
        EmployeeFamilyInfo employeeFamilyInfo2 = new EmployeeFamilyInfo();
        employeeFamilyInfo2.setId(employeeFamilyInfo1.getId());
        assertThat(employeeFamilyInfo1).isEqualTo(employeeFamilyInfo2);
        employeeFamilyInfo2.setId(2L);
        assertThat(employeeFamilyInfo1).isNotEqualTo(employeeFamilyInfo2);
        employeeFamilyInfo1.setId(null);
        assertThat(employeeFamilyInfo1).isNotEqualTo(employeeFamilyInfo2);
    }
}
