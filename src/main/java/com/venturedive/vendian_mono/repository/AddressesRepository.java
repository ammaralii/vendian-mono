package com.venturedive.vendian_mono.repository;

import com.venturedive.vendian_mono.domain.Addresses;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Addresses entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressesRepository extends JpaRepository<Addresses, Long>, JpaSpecificationExecutor<Addresses> {}
