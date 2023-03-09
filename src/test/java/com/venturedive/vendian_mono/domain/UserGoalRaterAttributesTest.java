package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserGoalRaterAttributesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserGoalRaterAttributes.class);
        UserGoalRaterAttributes userGoalRaterAttributes1 = new UserGoalRaterAttributes();
        userGoalRaterAttributes1.setId(1L);
        UserGoalRaterAttributes userGoalRaterAttributes2 = new UserGoalRaterAttributes();
        userGoalRaterAttributes2.setId(userGoalRaterAttributes1.getId());
        assertThat(userGoalRaterAttributes1).isEqualTo(userGoalRaterAttributes2);
        userGoalRaterAttributes2.setId(2L);
        assertThat(userGoalRaterAttributes1).isNotEqualTo(userGoalRaterAttributes2);
        userGoalRaterAttributes1.setId(null);
        assertThat(userGoalRaterAttributes1).isNotEqualTo(userGoalRaterAttributes2);
    }
}
