package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WorkLogsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkLogs.class);
        WorkLogs workLogs1 = new WorkLogs();
        workLogs1.setId(1L);
        WorkLogs workLogs2 = new WorkLogs();
        workLogs2.setId(workLogs1.getId());
        assertThat(workLogs1).isEqualTo(workLogs2);
        workLogs2.setId(2L);
        assertThat(workLogs1).isNotEqualTo(workLogs2);
        workLogs1.setId(null);
        assertThat(workLogs1).isNotEqualTo(workLogs2);
    }
}
