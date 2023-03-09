package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeEmergencyContactsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeEmergencyContacts.class);
        EmployeeEmergencyContacts employeeEmergencyContacts1 = new EmployeeEmergencyContacts();
        employeeEmergencyContacts1.setId(1L);
        EmployeeEmergencyContacts employeeEmergencyContacts2 = new EmployeeEmergencyContacts();
        employeeEmergencyContacts2.setId(employeeEmergencyContacts1.getId());
        assertThat(employeeEmergencyContacts1).isEqualTo(employeeEmergencyContacts2);
        employeeEmergencyContacts2.setId(2L);
        assertThat(employeeEmergencyContacts1).isNotEqualTo(employeeEmergencyContacts2);
        employeeEmergencyContacts1.setId(null);
        assertThat(employeeEmergencyContacts1).isNotEqualTo(employeeEmergencyContacts2);
    }
}
