package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QualificationTypesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QualificationTypes.class);
        QualificationTypes qualificationTypes1 = new QualificationTypes();
        qualificationTypes1.setId(1L);
        QualificationTypes qualificationTypes2 = new QualificationTypes();
        qualificationTypes2.setId(qualificationTypes1.getId());
        assertThat(qualificationTypes1).isEqualTo(qualificationTypes2);
        qualificationTypes2.setId(2L);
        assertThat(qualificationTypes1).isNotEqualTo(qualificationTypes2);
        qualificationTypes1.setId(null);
        assertThat(qualificationTypes1).isNotEqualTo(qualificationTypes2);
    }
}
