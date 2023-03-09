package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeProfileHistoryLogsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeProfileHistoryLogs.class);
        EmployeeProfileHistoryLogs employeeProfileHistoryLogs1 = new EmployeeProfileHistoryLogs();
        employeeProfileHistoryLogs1.setId(1L);
        EmployeeProfileHistoryLogs employeeProfileHistoryLogs2 = new EmployeeProfileHistoryLogs();
        employeeProfileHistoryLogs2.setId(employeeProfileHistoryLogs1.getId());
        assertThat(employeeProfileHistoryLogs1).isEqualTo(employeeProfileHistoryLogs2);
        employeeProfileHistoryLogs2.setId(2L);
        assertThat(employeeProfileHistoryLogs1).isNotEqualTo(employeeProfileHistoryLogs2);
        employeeProfileHistoryLogs1.setId(null);
        assertThat(employeeProfileHistoryLogs1).isNotEqualTo(employeeProfileHistoryLogs2);
    }
}
