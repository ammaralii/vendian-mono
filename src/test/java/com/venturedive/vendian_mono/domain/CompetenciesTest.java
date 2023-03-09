package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompetenciesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Competencies.class);
        Competencies competencies1 = new Competencies();
        competencies1.setId(1L);
        Competencies competencies2 = new Competencies();
        competencies2.setId(competencies1.getId());
        assertThat(competencies1).isEqualTo(competencies2);
        competencies2.setId(2L);
        assertThat(competencies1).isNotEqualTo(competencies2);
        competencies1.setId(null);
        assertThat(competencies1).isNotEqualTo(competencies2);
    }
}
