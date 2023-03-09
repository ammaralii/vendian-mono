package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GoalGroupMappingsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoalGroupMappings.class);
        GoalGroupMappings goalGroupMappings1 = new GoalGroupMappings();
        goalGroupMappings1.setId(1L);
        GoalGroupMappings goalGroupMappings2 = new GoalGroupMappings();
        goalGroupMappings2.setId(goalGroupMappings1.getId());
        assertThat(goalGroupMappings1).isEqualTo(goalGroupMappings2);
        goalGroupMappings2.setId(2L);
        assertThat(goalGroupMappings1).isNotEqualTo(goalGroupMappings2);
        goalGroupMappings1.setId(null);
        assertThat(goalGroupMappings1).isNotEqualTo(goalGroupMappings2);
    }
}
