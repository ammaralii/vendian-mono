package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserAttributeEscalationRulesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAttributeEscalationRules.class);
        UserAttributeEscalationRules userAttributeEscalationRules1 = new UserAttributeEscalationRules();
        userAttributeEscalationRules1.setId(1L);
        UserAttributeEscalationRules userAttributeEscalationRules2 = new UserAttributeEscalationRules();
        userAttributeEscalationRules2.setId(userAttributeEscalationRules1.getId());
        assertThat(userAttributeEscalationRules1).isEqualTo(userAttributeEscalationRules2);
        userAttributeEscalationRules2.setId(2L);
        assertThat(userAttributeEscalationRules1).isNotEqualTo(userAttributeEscalationRules2);
        userAttributeEscalationRules1.setId(null);
        assertThat(userAttributeEscalationRules1).isNotEqualTo(userAttributeEscalationRules2);
    }
}
