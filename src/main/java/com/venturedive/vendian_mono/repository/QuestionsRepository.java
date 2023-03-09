package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.Questions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Questions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionsRepository extends JpaRepository<Questions, Long>, JpaSpecificationExecutor<Questions> {}
