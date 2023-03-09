package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DealResourceSkillsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DealResourceSkills.class);
        DealResourceSkills dealResourceSkills1 = new DealResourceSkills();
        dealResourceSkills1.setId(1L);
        DealResourceSkills dealResourceSkills2 = new DealResourceSkills();
        dealResourceSkills2.setId(dealResourceSkills1.getId());
        assertThat(dealResourceSkills1).isEqualTo(dealResourceSkills2);
        dealResourceSkills2.setId(2L);
        assertThat(dealResourceSkills1).isNotEqualTo(dealResourceSkills2);
        dealResourceSkills1.setId(null);
        assertThat(dealResourceSkills1).isNotEqualTo(dealResourceSkills2);
    }
}
