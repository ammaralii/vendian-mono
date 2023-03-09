package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RolePermissionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RolePermissions.class);
        RolePermissions rolePermissions1 = new RolePermissions();
        rolePermissions1.setId(1L);
        RolePermissions rolePermissions2 = new RolePermissions();
        rolePermissions2.setId(rolePermissions1.getId());
        assertThat(rolePermissions1).isEqualTo(rolePermissions2);
        rolePermissions2.setId(2L);
        assertThat(rolePermissions1).isNotEqualTo(rolePermissions2);
        rolePermissions1.setId(null);
        assertThat(rolePermissions1).isNotEqualTo(rolePermissions2);
    }
}
