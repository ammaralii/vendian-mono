package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.Religions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Religions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReligionsRepository extends JpaRepository<Religions, Long>, JpaSpecificationExecutor<Religions> {}
