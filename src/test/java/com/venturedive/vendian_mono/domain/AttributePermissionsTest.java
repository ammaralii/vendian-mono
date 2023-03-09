package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttributePermissionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttributePermissions.class);
        AttributePermissions attributePermissions1 = new AttributePermissions();
        attributePermissions1.setId(1L);
        AttributePermissions attributePermissions2 = new AttributePermissions();
        attributePermissions2.setId(attributePermissions1.getId());
        assertThat(attributePermissions1).isEqualTo(attributePermissions2);
        attributePermissions2.setId(2L);
        assertThat(attributePermissions1).isNotEqualTo(attributePermissions2);
        attributePermissions1.setId(null);
        assertThat(attributePermissions1).isNotEqualTo(attributePermissions2);
    }
}
