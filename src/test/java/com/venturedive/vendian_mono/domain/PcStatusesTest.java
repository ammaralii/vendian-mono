package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PcStatusesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PcStatuses.class);
        PcStatuses pcStatuses1 = new PcStatuses();
        pcStatuses1.setId(1L);
        PcStatuses pcStatuses2 = new PcStatuses();
        pcStatuses2.setId(pcStatuses1.getId());
        assertThat(pcStatuses1).isEqualTo(pcStatuses2);
        pcStatuses2.setId(2L);
        assertThat(pcStatuses1).isNotEqualTo(pcStatuses2);
        pcStatuses1.setId(null);
        assertThat(pcStatuses1).isNotEqualTo(pcStatuses2);
    }
}
