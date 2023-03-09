package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.AttributePermissions;
import com.venturedive.vendian_mono.repository.AttributePermissionsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AttributePermissions}.
 */
@Service
@Transactional
public class AttributePermissionsService {

    private final Logger log = LoggerFactory.getLogger(AttributePermissionsService.class);

    private final AttributePermissionsRepository attributePermissionsRepository;

    public AttributePermissionsService(AttributePermissionsRepository attributePermissionsRepository) {
        this.attributePermissionsRepository = attributePermissionsRepository;
    }

    /**
     * Save a attributePermissions.
     *
     * @param attributePermissions the entity to save.
     * @return the persisted entity.
     */
    public AttributePermissions save(AttributePermissions attributePermissions) {
        log.debug("Request to save AttributePermissions : {}", attributePermissions);
        return attributePermissionsRepository.save(attributePermissions);
    }

    /**
     * Update a attributePermissions.
     *
     * @param attributePermissions the entity to save.
     * @return the persisted entity.
     */
    public AttributePermissions update(AttributePermissions attributePermissions) {
        log.debug("Request to update AttributePermissions : {}", attributePermissions);
        return attributePermissionsRepository.save(attributePermissions);
    }

    /**
     * Partially update a attributePermissions.
     *
     * @param attributePermissions the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AttributePermissions> partialUpdate(AttributePermissions attributePermissions) {
        log.debug("Request to partially update AttributePermissions : {}", attributePermissions);

        return attributePermissionsRepository
            .findById(attributePermissions.getId())
            .map(existingAttributePermissions -> {
                if (attributePermissions.getMethod() != null) {
                    existingAttributePermissions.setMethod(attributePermissions.getMethod());
                }
                if (attributePermissions.getRoute() != null) {
                    existingAttributePermissions.setRoute(attributePermissions.getRoute());
                }
                if (attributePermissions.getResponsepermissions() != null) {
                    existingAttributePermissions.setResponsepermissions(attributePermissions.getResponsepermissions());
                }
                if (attributePermissions.getRequestpermissions() != null) {
                    existingAttributePermissions.setRequestpermissions(attributePermissions.getRequestpermissions());
                }
                if (attributePermissions.getCreatedat() != null) {
                    existingAttributePermissions.setCreatedat(attributePermissions.getCreatedat());
                }
                if (attributePermissions.getUpdatedat() != null) {
                    existingAttributePermissions.setUpdatedat(attributePermissions.getUpdatedat());
                }

                return existingAttributePermissions;
            })
            .map(attributePermissionsRepository::save);
    }

    /**
     * Get all the attributePermissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AttributePermissions> findAll(Pageable pageable) {
        log.debug("Request to get all AttributePermissions");
        return attributePermissionsRepository.findAll(pageable);
    }

    /**
     * Get one attributePermissions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AttributePermissions> findOne(Long id) {
        log.debug("Request to get AttributePermissions : {}", id);
        return attributePermissionsRepository.findById(id);
    }

    /**
     * Delete the attributePermissions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AttributePermissions : {}", id);
        attributePermissionsRepository.deleteById(id);
    }
}
