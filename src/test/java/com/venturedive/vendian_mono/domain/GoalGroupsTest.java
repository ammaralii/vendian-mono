package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GoalGroupsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoalGroups.class);
        GoalGroups goalGroups1 = new GoalGroups();
        goalGroups1.setId(1L);
        GoalGroups goalGroups2 = new GoalGroups();
        goalGroups2.setId(goalGroups1.getId());
        assertThat(goalGroups1).isEqualTo(goalGroups2);
        goalGroups2.setId(2L);
        assertThat(goalGroups1).isNotEqualTo(goalGroups2);
        goalGroups1.setId(null);
        assertThat(goalGroups1).isNotEqualTo(goalGroups2);
    }
}
