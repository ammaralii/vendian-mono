package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AuditLogsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuditLogs.class);
        AuditLogs auditLogs1 = new AuditLogs();
        auditLogs1.setId(1L);
        AuditLogs auditLogs2 = new AuditLogs();
        auditLogs2.setId(auditLogs1.getId());
        assertThat(auditLogs1).isEqualTo(auditLogs2);
        auditLogs2.setId(2L);
        assertThat(auditLogs1).isNotEqualTo(auditLogs2);
        auditLogs1.setId(null);
        assertThat(auditLogs1).isNotEqualTo(auditLogs2);
    }
}
