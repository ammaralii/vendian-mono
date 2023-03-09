package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectRolesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectRoles.class);
        ProjectRoles projectRoles1 = new ProjectRoles();
        projectRoles1.setId(1L);
        ProjectRoles projectRoles2 = new ProjectRoles();
        projectRoles2.setId(projectRoles1.getId());
        assertThat(projectRoles1).isEqualTo(projectRoles2);
        projectRoles2.setId(2L);
        assertThat(projectRoles1).isNotEqualTo(projectRoles2);
        projectRoles1.setId(null);
        assertThat(projectRoles1).isNotEqualTo(projectRoles2);
    }
}
