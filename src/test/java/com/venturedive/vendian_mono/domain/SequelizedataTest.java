package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SequelizedataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sequelizedata.class);
        Sequelizedata sequelizedata1 = new Sequelizedata();
        sequelizedata1.setId(1L);
        Sequelizedata sequelizedata2 = new Sequelizedata();
        sequelizedata2.setId(sequelizedata1.getId());
        assertThat(sequelizedata1).isEqualTo(sequelizedata2);
        sequelizedata2.setId(2L);
        assertThat(sequelizedata1).isNotEqualTo(sequelizedata2);
        sequelizedata1.setId(null);
        assertThat(sequelizedata1).isNotEqualTo(sequelizedata2);
    }
}
