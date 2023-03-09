package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeContactsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeContacts.class);
        EmployeeContacts employeeContacts1 = new EmployeeContacts();
        employeeContacts1.setId(1L);
        EmployeeContacts employeeContacts2 = new EmployeeContacts();
        employeeContacts2.setId(employeeContacts1.getId());
        assertThat(employeeContacts1).isEqualTo(employeeContacts2);
        employeeContacts2.setId(2L);
        assertThat(employeeContacts1).isNotEqualTo(employeeContacts2);
        employeeContacts1.setId(null);
        assertThat(employeeContacts1).isNotEqualTo(employeeContacts2);
    }
}
