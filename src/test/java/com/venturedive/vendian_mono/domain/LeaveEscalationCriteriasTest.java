package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeaveEscalationCriteriasTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveEscalationCriterias.class);
        LeaveEscalationCriterias leaveEscalationCriterias1 = new LeaveEscalationCriterias();
        leaveEscalationCriterias1.setId(1L);
        LeaveEscalationCriterias leaveEscalationCriterias2 = new LeaveEscalationCriterias();
        leaveEscalationCriterias2.setId(leaveEscalationCriterias1.getId());
        assertThat(leaveEscalationCriterias1).isEqualTo(leaveEscalationCriterias2);
        leaveEscalationCriterias2.setId(2L);
        assertThat(leaveEscalationCriterias1).isNotEqualTo(leaveEscalationCriterias2);
        leaveEscalationCriterias1.setId(null);
        assertThat(leaveEscalationCriterias1).isNotEqualTo(leaveEscalationCriterias2);
    }
}
