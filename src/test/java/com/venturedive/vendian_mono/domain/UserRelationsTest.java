package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserRelationsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserRelations.class);
        UserRelations userRelations1 = new UserRelations();
        userRelations1.setId(1L);
        UserRelations userRelations2 = new UserRelations();
        userRelations2.setId(userRelations1.getId());
        assertThat(userRelations1).isEqualTo(userRelations2);
        userRelations2.setId(2L);
        assertThat(userRelations1).isNotEqualTo(userRelations2);
        userRelations1.setId(null);
        assertThat(userRelations1).isNotEqualTo(userRelations2);
    }
}
