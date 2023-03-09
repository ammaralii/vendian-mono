package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeAddressesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeAddresses.class);
        EmployeeAddresses employeeAddresses1 = new EmployeeAddresses();
        employeeAddresses1.setId(1L);
        EmployeeAddresses employeeAddresses2 = new EmployeeAddresses();
        employeeAddresses2.setId(employeeAddresses1.getId());
        assertThat(employeeAddresses1).isEqualTo(employeeAddresses2);
        employeeAddresses2.setId(2L);
        assertThat(employeeAddresses1).isNotEqualTo(employeeAddresses2);
        employeeAddresses1.setId(null);
        assertThat(employeeAddresses1).isNotEqualTo(employeeAddresses2);
    }
}
