package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.Configurations;
import com.venturedive.vendian_mono.repository.ConfigurationsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Configurations}.
 */
@Service
@Transactional
public class ConfigurationsService {

    private final Logger log = LoggerFactory.getLogger(ConfigurationsService.class);

    private final ConfigurationsRepository configurationsRepository;

    public ConfigurationsService(ConfigurationsRepository configurationsRepository) {
        this.configurationsRepository = configurationsRepository;
    }

    /**
     * Save a configurations.
     *
     * @param configurations the entity to save.
     * @return the persisted entity.
     */
    public Configurations save(Configurations configurations) {
        log.debug("Request to save Configurations : {}", configurations);
        return configurationsRepository.save(configurations);
    }

    /**
     * Update a configurations.
     *
     * @param configurations the entity to save.
     * @return the persisted entity.
     */
    public Configurations update(Configurations configurations) {
        log.debug("Request to update Configurations : {}", configurations);
        return configurationsRepository.save(configurations);
    }

    /**
     * Partially update a configurations.
     *
     * @param configurations the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Configurations> partialUpdate(Configurations configurations) {
        log.debug("Request to partially update Configurations : {}", configurations);

        return configurationsRepository
            .findById(configurations.getId())
            .map(existingConfigurations -> {
                if (configurations.getName() != null) {
                    existingConfigurations.setName(configurations.getName());
                }
                if (configurations.getGroup() != null) {
                    existingConfigurations.setGroup(configurations.getGroup());
                }
                if (configurations.getIntValue() != null) {
                    existingConfigurations.setIntValue(configurations.getIntValue());
                }
                if (configurations.getStringValue() != null) {
                    existingConfigurations.setStringValue(configurations.getStringValue());
                }
                if (configurations.getDoubleValue() != null) {
                    existingConfigurations.setDoubleValue(configurations.getDoubleValue());
                }
                if (configurations.getDateValue() != null) {
                    existingConfigurations.setDateValue(configurations.getDateValue());
                }
                if (configurations.getJsonValue() != null) {
                    existingConfigurations.setJsonValue(configurations.getJsonValue());
                }
                if (configurations.getCreatedAt() != null) {
                    existingConfigurations.setCreatedAt(configurations.getCreatedAt());
                }
                if (configurations.getUpdatedAt() != null) {
                    existingConfigurations.setUpdatedAt(configurations.getUpdatedAt());
                }
                if (configurations.getDeletedAt() != null) {
                    existingConfigurations.setDeletedAt(configurations.getDeletedAt());
                }
                if (configurations.getVersion() != null) {
                    existingConfigurations.setVersion(configurations.getVersion());
                }

                return existingConfigurations;
            })
            .map(configurationsRepository::save);
    }

    /**
     * Get all the configurations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Configurations> findAll(Pageable pageable) {
        log.debug("Request to get all Configurations");
        return configurationsRepository.findAll(pageable);
    }

    /**
     * Get one configurations by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Configurations> findOne(Long id) {
        log.debug("Request to get Configurations : {}", id);
        return configurationsRepository.findById(id);
    }

    /**
     * Delete the configurations by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Configurations : {}", id);
        configurationsRepository.deleteById(id);
    }
}
