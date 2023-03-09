package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmploymentStatusesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmploymentStatuses.class);
        EmploymentStatuses employmentStatuses1 = new EmploymentStatuses();
        employmentStatuses1.setId(1L);
        EmploymentStatuses employmentStatuses2 = new EmploymentStatuses();
        employmentStatuses2.setId(employmentStatuses1.getId());
        assertThat(employmentStatuses1).isEqualTo(employmentStatuses2);
        employmentStatuses2.setId(2L);
        assertThat(employmentStatuses1).isNotEqualTo(employmentStatuses2);
        employmentStatuses1.setId(null);
        assertThat(employmentStatuses1).isNotEqualTo(employmentStatuses2);
    }
}
