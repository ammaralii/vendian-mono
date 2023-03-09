package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TracksTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tracks.class);
        Tracks tracks1 = new Tracks();
        tracks1.setId(1L);
        Tracks tracks2 = new Tracks();
        tracks2.setId(tracks1.getId());
        assertThat(tracks1).isEqualTo(tracks2);
        tracks2.setId(2L);
        assertThat(tracks1).isNotEqualTo(tracks2);
        tracks1.setId(null);
        assertThat(tracks1).isNotEqualTo(tracks2);
    }
}
