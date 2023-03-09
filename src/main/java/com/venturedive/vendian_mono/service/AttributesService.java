package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.Attributes;
import com.venturedive.vendian_mono.repository.AttributesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Attributes}.
 */
@Service
@Transactional
public class AttributesService {

    private final Logger log = LoggerFactory.getLogger(AttributesService.class);

    private final AttributesRepository attributesRepository;

    public AttributesService(AttributesRepository attributesRepository) {
        this.attributesRepository = attributesRepository;
    }

    /**
     * Save a attributes.
     *
     * @param attributes the entity to save.
     * @return the persisted entity.
     */
    public Attributes save(Attributes attributes) {
        log.debug("Request to save Attributes : {}", attributes);
        return attributesRepository.save(attributes);
    }

    /**
     * Update a attributes.
     *
     * @param attributes the entity to save.
     * @return the persisted entity.
     */
    public Attributes update(Attributes attributes) {
        log.debug("Request to update Attributes : {}", attributes);
        return attributesRepository.save(attributes);
    }

    /**
     * Partially update a attributes.
     *
     * @param attributes the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Attributes> partialUpdate(Attributes attributes) {
        log.debug("Request to partially update Attributes : {}", attributes);

        return attributesRepository
            .findById(attributes.getId())
            .map(existingAttributes -> {
                if (attributes.getName() != null) {
                    existingAttributes.setName(attributes.getName());
                }
                if (attributes.getCreatedAt() != null) {
                    existingAttributes.setCreatedAt(attributes.getCreatedAt());
                }
                if (attributes.getUpdatedAt() != null) {
                    existingAttributes.setUpdatedAt(attributes.getUpdatedAt());
                }
                if (attributes.getEndDate() != null) {
                    existingAttributes.setEndDate(attributes.getEndDate());
                }
                if (attributes.getVersion() != null) {
                    existingAttributes.setVersion(attributes.getVersion());
                }
                if (attributes.getEffDate() != null) {
                    existingAttributes.setEffDate(attributes.getEffDate());
                }

                return existingAttributes;
            })
            .map(attributesRepository::save);
    }

    /**
     * Get all the attributes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Attributes> findAll(Pageable pageable) {
        log.debug("Request to get all Attributes");
        return attributesRepository.findAll(pageable);
    }

    /**
     * Get one attributes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Attributes> findOne(Long id) {
        log.debug("Request to get Attributes : {}", id);
        return attributesRepository.findById(id);
    }

    /**
     * Delete the attributes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Attributes : {}", id);
        attributesRepository.deleteById(id);
    }
}
