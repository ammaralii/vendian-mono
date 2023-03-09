package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ZEmployeeProjectsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ZEmployeeProjects.class);
        ZEmployeeProjects zEmployeeProjects1 = new ZEmployeeProjects();
        zEmployeeProjects1.setId(1L);
        ZEmployeeProjects zEmployeeProjects2 = new ZEmployeeProjects();
        zEmployeeProjects2.setId(zEmployeeProjects1.getId());
        assertThat(zEmployeeProjects1).isEqualTo(zEmployeeProjects2);
        zEmployeeProjects2.setId(2L);
        assertThat(zEmployeeProjects1).isNotEqualTo(zEmployeeProjects2);
        zEmployeeProjects1.setId(null);
        assertThat(zEmployeeProjects1).isNotEqualTo(zEmployeeProjects2);
    }
}
