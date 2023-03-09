package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QuestionsProcessingEventLogsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionsProcessingEventLogs.class);
        QuestionsProcessingEventLogs questionsProcessingEventLogs1 = new QuestionsProcessingEventLogs();
        questionsProcessingEventLogs1.setId(1L);
        QuestionsProcessingEventLogs questionsProcessingEventLogs2 = new QuestionsProcessingEventLogs();
        questionsProcessingEventLogs2.setId(questionsProcessingEventLogs1.getId());
        assertThat(questionsProcessingEventLogs1).isEqualTo(questionsProcessingEventLogs2);
        questionsProcessingEventLogs2.setId(2L);
        assertThat(questionsProcessingEventLogs1).isNotEqualTo(questionsProcessingEventLogs2);
        questionsProcessingEventLogs1.setId(null);
        assertThat(questionsProcessingEventLogs1).isNotEqualTo(questionsProcessingEventLogs2);
    }
}
