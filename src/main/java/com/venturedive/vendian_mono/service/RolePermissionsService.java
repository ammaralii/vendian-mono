package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.RolePermissions;
import com.venturedive.vendian_mono.repository.RolePermissionsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RolePermissions}.
 */
@Service
@Transactional
public class RolePermissionsService {

    private final Logger log = LoggerFactory.getLogger(RolePermissionsService.class);

    private final RolePermissionsRepository rolePermissionsRepository;

    public RolePermissionsService(RolePermissionsRepository rolePermissionsRepository) {
        this.rolePermissionsRepository = rolePermissionsRepository;
    }

    /**
     * Save a rolePermissions.
     *
     * @param rolePermissions the entity to save.
     * @return the persisted entity.
     */
    public RolePermissions save(RolePermissions rolePermissions) {
        log.debug("Request to save RolePermissions : {}", rolePermissions);
        return rolePermissionsRepository.save(rolePermissions);
    }

    /**
     * Update a rolePermissions.
     *
     * @param rolePermissions the entity to save.
     * @return the persisted entity.
     */
    public RolePermissions update(RolePermissions rolePermissions) {
        log.debug("Request to update RolePermissions : {}", rolePermissions);
        return rolePermissionsRepository.save(rolePermissions);
    }

    /**
     * Partially update a rolePermissions.
     *
     * @param rolePermissions the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RolePermissions> partialUpdate(RolePermissions rolePermissions) {
        log.debug("Request to partially update RolePermissions : {}", rolePermissions);

        return rolePermissionsRepository
            .findById(rolePermissions.getId())
            .map(existingRolePermissions -> {
                if (rolePermissions.getCreatedat() != null) {
                    existingRolePermissions.setCreatedat(rolePermissions.getCreatedat());
                }
                if (rolePermissions.getUpdatedat() != null) {
                    existingRolePermissions.setUpdatedat(rolePermissions.getUpdatedat());
                }

                return existingRolePermissions;
            })
            .map(rolePermissionsRepository::save);
    }

    /**
     * Get all the rolePermissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RolePermissions> findAll(Pageable pageable) {
        log.debug("Request to get all RolePermissions");
        return rolePermissionsRepository.findAll(pageable);
    }

    /**
     * Get one rolePermissions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RolePermissions> findOne(Long id) {
        log.debug("Request to get RolePermissions : {}", id);
        return rolePermissionsRepository.findById(id);
    }

    /**
     * Delete the rolePermissions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RolePermissions : {}", id);
        rolePermissionsRepository.deleteById(id);
    }
}
