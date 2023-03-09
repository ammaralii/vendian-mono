package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserAttributeApprovalRulesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAttributeApprovalRules.class);
        UserAttributeApprovalRules userAttributeApprovalRules1 = new UserAttributeApprovalRules();
        userAttributeApprovalRules1.setId(1L);
        UserAttributeApprovalRules userAttributeApprovalRules2 = new UserAttributeApprovalRules();
        userAttributeApprovalRules2.setId(userAttributeApprovalRules1.getId());
        assertThat(userAttributeApprovalRules1).isEqualTo(userAttributeApprovalRules2);
        userAttributeApprovalRules2.setId(2L);
        assertThat(userAttributeApprovalRules1).isNotEqualTo(userAttributeApprovalRules2);
        userAttributeApprovalRules1.setId(null);
        assertThat(userAttributeApprovalRules1).isNotEqualTo(userAttributeApprovalRules2);
    }
}
