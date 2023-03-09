package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.Sequelizedata;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Sequelizedata entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SequelizedataRepository extends JpaRepository<Sequelizedata, Long>, JpaSpecificationExecutor<Sequelizedata> {}
