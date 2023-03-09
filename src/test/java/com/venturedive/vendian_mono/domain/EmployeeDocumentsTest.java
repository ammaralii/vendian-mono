package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeDocumentsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeDocuments.class);
        EmployeeDocuments employeeDocuments1 = new EmployeeDocuments();
        employeeDocuments1.setId(1L);
        EmployeeDocuments employeeDocuments2 = new EmployeeDocuments();
        employeeDocuments2.setId(employeeDocuments1.getId());
        assertThat(employeeDocuments1).isEqualTo(employeeDocuments2);
        employeeDocuments2.setId(2L);
        assertThat(employeeDocuments1).isNotEqualTo(employeeDocuments2);
        employeeDocuments1.setId(null);
        assertThat(employeeDocuments1).isNotEqualTo(employeeDocuments2);
    }
}
