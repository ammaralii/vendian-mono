package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserRatingsRemarksTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserRatingsRemarks.class);
        UserRatingsRemarks userRatingsRemarks1 = new UserRatingsRemarks();
        userRatingsRemarks1.setId(1L);
        UserRatingsRemarks userRatingsRemarks2 = new UserRatingsRemarks();
        userRatingsRemarks2.setId(userRatingsRemarks1.getId());
        assertThat(userRatingsRemarks1).isEqualTo(userRatingsRemarks2);
        userRatingsRemarks2.setId(2L);
        assertThat(userRatingsRemarks1).isNotEqualTo(userRatingsRemarks2);
        userRatingsRemarks1.setId(null);
        assertThat(userRatingsRemarks1).isNotEqualTo(userRatingsRemarks2);
    }
}
