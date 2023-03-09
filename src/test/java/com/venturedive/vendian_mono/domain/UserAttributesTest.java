package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserAttributesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAttributes.class);
        UserAttributes userAttributes1 = new UserAttributes();
        userAttributes1.setId(1L);
        UserAttributes userAttributes2 = new UserAttributes();
        userAttributes2.setId(userAttributes1.getId());
        assertThat(userAttributes1).isEqualTo(userAttributes2);
        userAttributes2.setId(2L);
        assertThat(userAttributes1).isNotEqualTo(userAttributes2);
        userAttributes1.setId(null);
        assertThat(userAttributes1).isNotEqualTo(userAttributes2);
    }
}
