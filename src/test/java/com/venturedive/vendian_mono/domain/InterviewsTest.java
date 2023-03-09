package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InterviewsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Interviews.class);
        Interviews interviews1 = new Interviews();
        interviews1.setId(1L);
        Interviews interviews2 = new Interviews();
        interviews2.setId(interviews1.getId());
        assertThat(interviews1).isEqualTo(interviews2);
        interviews2.setId(2L);
        assertThat(interviews1).isNotEqualTo(interviews2);
        interviews1.setId(null);
        assertThat(interviews1).isNotEqualTo(interviews2);
    }
}
