package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserRelationApprovalRulesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserRelationApprovalRules.class);
        UserRelationApprovalRules userRelationApprovalRules1 = new UserRelationApprovalRules();
        userRelationApprovalRules1.setId(1L);
        UserRelationApprovalRules userRelationApprovalRules2 = new UserRelationApprovalRules();
        userRelationApprovalRules2.setId(userRelationApprovalRules1.getId());
        assertThat(userRelationApprovalRules1).isEqualTo(userRelationApprovalRules2);
        userRelationApprovalRules2.setId(2L);
        assertThat(userRelationApprovalRules1).isNotEqualTo(userRelationApprovalRules2);
        userRelationApprovalRules1.setId(null);
        assertThat(userRelationApprovalRules1).isNotEqualTo(userRelationApprovalRules2);
    }
}
