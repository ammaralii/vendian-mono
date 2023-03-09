package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeCertificatesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeCertificates.class);
        EmployeeCertificates employeeCertificates1 = new EmployeeCertificates();
        employeeCertificates1.setId(1L);
        EmployeeCertificates employeeCertificates2 = new EmployeeCertificates();
        employeeCertificates2.setId(employeeCertificates1.getId());
        assertThat(employeeCertificates1).isEqualTo(employeeCertificates2);
        employeeCertificates2.setId(2L);
        assertThat(employeeCertificates1).isNotEqualTo(employeeCertificates2);
        employeeCertificates1.setId(null);
        assertThat(employeeCertificates1).isNotEqualTo(employeeCertificates2);
    }
}
