package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PerformanceCycleEmployeeRatingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerformanceCycleEmployeeRating.class);
        PerformanceCycleEmployeeRating performanceCycleEmployeeRating1 = new PerformanceCycleEmployeeRating();
        performanceCycleEmployeeRating1.setId(1L);
        PerformanceCycleEmployeeRating performanceCycleEmployeeRating2 = new PerformanceCycleEmployeeRating();
        performanceCycleEmployeeRating2.setId(performanceCycleEmployeeRating1.getId());
        assertThat(performanceCycleEmployeeRating1).isEqualTo(performanceCycleEmployeeRating2);
        performanceCycleEmployeeRating2.setId(2L);
        assertThat(performanceCycleEmployeeRating1).isNotEqualTo(performanceCycleEmployeeRating2);
        performanceCycleEmployeeRating1.setId(null);
        assertThat(performanceCycleEmployeeRating1).isNotEqualTo(performanceCycleEmployeeRating2);
    }
}
