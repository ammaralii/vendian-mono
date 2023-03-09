package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.Permissions;
import com.venturedive.vendian_mono.repository.PermissionsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Permissions}.
 */
@Service
@Transactional
public class PermissionsService {

    private final Logger log = LoggerFactory.getLogger(PermissionsService.class);

    private final PermissionsRepository permissionsRepository;

    public PermissionsService(PermissionsRepository permissionsRepository) {
        this.permissionsRepository = permissionsRepository;
    }

    /**
     * Save a permissions.
     *
     * @param permissions the entity to save.
     * @return the persisted entity.
     */
    public Permissions save(Permissions permissions) {
        log.debug("Request to save Permissions : {}", permissions);
        return permissionsRepository.save(permissions);
    }

    /**
     * Update a permissions.
     *
     * @param permissions the entity to save.
     * @return the persisted entity.
     */
    public Permissions update(Permissions permissions) {
        log.debug("Request to update Permissions : {}", permissions);
        return permissionsRepository.save(permissions);
    }

    /**
     * Partially update a permissions.
     *
     * @param permissions the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Permissions> partialUpdate(Permissions permissions) {
        log.debug("Request to partially update Permissions : {}", permissions);

        return permissionsRepository
            .findById(permissions.getId())
            .map(existingPermissions -> {
                if (permissions.getName() != null) {
                    existingPermissions.setName(permissions.getName());
                }
                if (permissions.getDescription() != null) {
                    existingPermissions.setDescription(permissions.getDescription());
                }
                if (permissions.getGroupName() != null) {
                    existingPermissions.setGroupName(permissions.getGroupName());
                }
                if (permissions.getIsactive() != null) {
                    existingPermissions.setIsactive(permissions.getIsactive());
                }
                if (permissions.getCreatedat() != null) {
                    existingPermissions.setCreatedat(permissions.getCreatedat());
                }
                if (permissions.getUpdatedat() != null) {
                    existingPermissions.setUpdatedat(permissions.getUpdatedat());
                }

                return existingPermissions;
            })
            .map(permissionsRepository::save);
    }

    /**
     * Get all the permissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Permissions> findAll(Pageable pageable) {
        log.debug("Request to get all Permissions");
        return permissionsRepository.findAll(pageable);
    }

    /**
     * Get one permissions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Permissions> findOne(Long id) {
        log.debug("Request to get Permissions : {}", id);
        return permissionsRepository.findById(id);
    }

    /**
     * Delete the permissions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Permissions : {}", id);
        permissionsRepository.deleteById(id);
    }
}
