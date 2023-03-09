package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.Configs;
import com.venturedive.vendian_mono.repository.ConfigsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Configs}.
 */
@Service
@Transactional
public class ConfigsService {

    private final Logger log = LoggerFactory.getLogger(ConfigsService.class);

    private final ConfigsRepository configsRepository;

    public ConfigsService(ConfigsRepository configsRepository) {
        this.configsRepository = configsRepository;
    }

    /**
     * Save a configs.
     *
     * @param configs the entity to save.
     * @return the persisted entity.
     */
    public Configs save(Configs configs) {
        log.debug("Request to save Configs : {}", configs);
        return configsRepository.save(configs);
    }

    /**
     * Update a configs.
     *
     * @param configs the entity to save.
     * @return the persisted entity.
     */
    public Configs update(Configs configs) {
        log.debug("Request to update Configs : {}", configs);
        return configsRepository.save(configs);
    }

    /**
     * Partially update a configs.
     *
     * @param configs the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Configs> partialUpdate(Configs configs) {
        log.debug("Request to partially update Configs : {}", configs);

        return configsRepository
            .findById(configs.getId())
            .map(existingConfigs -> {
                if (configs.getName() != null) {
                    existingConfigs.setName(configs.getName());
                }
                if (configs.getGroup() != null) {
                    existingConfigs.setGroup(configs.getGroup());
                }
                if (configs.getIntvalue() != null) {
                    existingConfigs.setIntvalue(configs.getIntvalue());
                }
                if (configs.getStringvalue() != null) {
                    existingConfigs.setStringvalue(configs.getStringvalue());
                }
                if (configs.getDecimalvalue() != null) {
                    existingConfigs.setDecimalvalue(configs.getDecimalvalue());
                }
                if (configs.getJsonvalue() != null) {
                    existingConfigs.setJsonvalue(configs.getJsonvalue());
                }
                if (configs.getCreatedat() != null) {
                    existingConfigs.setCreatedat(configs.getCreatedat());
                }
                if (configs.getUpdatedat() != null) {
                    existingConfigs.setUpdatedat(configs.getUpdatedat());
                }

                return existingConfigs;
            })
            .map(configsRepository::save);
    }

    /**
     * Get all the configs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Configs> findAll(Pageable pageable) {
        log.debug("Request to get all Configs");
        return configsRepository.findAll(pageable);
    }

    /**
     * Get one configs by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Configs> findOne(Long id) {
        log.debug("Request to get Configs : {}", id);
        return configsRepository.findById(id);
    }

    /**
     * Delete the configs by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Configs : {}", id);
        configsRepository.deleteById(id);
    }
}
