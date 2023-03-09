package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.Roles;
import com.venturedive.vendian_mono.repository.RolesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Roles}.
 */
@Service
@Transactional
public class RolesService {

    private final Logger log = LoggerFactory.getLogger(RolesService.class);

    private final RolesRepository rolesRepository;

    public RolesService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    /**
     * Save a roles.
     *
     * @param roles the entity to save.
     * @return the persisted entity.
     */
    public Roles save(Roles roles) {
        log.debug("Request to save Roles : {}", roles);
        return rolesRepository.save(roles);
    }

    /**
     * Update a roles.
     *
     * @param roles the entity to save.
     * @return the persisted entity.
     */
    public Roles update(Roles roles) {
        log.debug("Request to update Roles : {}", roles);
        return rolesRepository.save(roles);
    }

    /**
     * Partially update a roles.
     *
     * @param roles the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Roles> partialUpdate(Roles roles) {
        log.debug("Request to partially update Roles : {}", roles);

        return rolesRepository
            .findById(roles.getId())
            .map(existingRoles -> {
                if (roles.getRole() != null) {
                    existingRoles.setRole(roles.getRole());
                }
                if (roles.getCreatedat() != null) {
                    existingRoles.setCreatedat(roles.getCreatedat());
                }
                if (roles.getUpdatedat() != null) {
                    existingRoles.setUpdatedat(roles.getUpdatedat());
                }

                return existingRoles;
            })
            .map(rolesRepository::save);
    }

    /**
     * Get all the roles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Roles> findAll(Pageable pageable) {
        log.debug("Request to get all Roles");
        return rolesRepository.findAll(pageable);
    }

    /**
     * Get one roles by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Roles> findOne(Long id) {
        log.debug("Request to get Roles : {}", id);
        return rolesRepository.findById(id);
    }

    /**
     * Delete the roles by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Roles : {}", id);
        rolesRepository.deleteById(id);
    }
}
