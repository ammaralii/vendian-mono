package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectCyclesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectCycles.class);
        ProjectCycles projectCycles1 = new ProjectCycles();
        projectCycles1.setId(1L);
        ProjectCycles projectCycles2 = new ProjectCycles();
        projectCycles2.setId(projectCycles1.getId());
        assertThat(projectCycles1).isEqualTo(projectCycles2);
        projectCycles2.setId(2L);
        assertThat(projectCycles1).isNotEqualTo(projectCycles2);
        projectCycles1.setId(null);
        assertThat(projectCycles1).isNotEqualTo(projectCycles2);
    }
}
