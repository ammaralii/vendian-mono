package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.Sequelizedata;
import com.venturedive.vendian_mono.repository.SequelizedataRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Sequelizedata}.
 */
@Service
@Transactional
public class SequelizedataService {

    private final Logger log = LoggerFactory.getLogger(SequelizedataService.class);

    private final SequelizedataRepository sequelizedataRepository;

    public SequelizedataService(SequelizedataRepository sequelizedataRepository) {
        this.sequelizedataRepository = sequelizedataRepository;
    }

    /**
     * Save a sequelizedata.
     *
     * @param sequelizedata the entity to save.
     * @return the persisted entity.
     */
    public Sequelizedata save(Sequelizedata sequelizedata) {
        log.debug("Request to save Sequelizedata : {}", sequelizedata);
        return sequelizedataRepository.save(sequelizedata);
    }

    /**
     * Update a sequelizedata.
     *
     * @param sequelizedata the entity to save.
     * @return the persisted entity.
     */
    public Sequelizedata update(Sequelizedata sequelizedata) {
        log.debug("Request to update Sequelizedata : {}", sequelizedata);
        return sequelizedataRepository.save(sequelizedata);
    }

    /**
     * Partially update a sequelizedata.
     *
     * @param sequelizedata the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Sequelizedata> partialUpdate(Sequelizedata sequelizedata) {
        log.debug("Request to partially update Sequelizedata : {}", sequelizedata);

        return sequelizedataRepository
            .findById(sequelizedata.getId())
            .map(existingSequelizedata -> {
                if (sequelizedata.getName() != null) {
                    existingSequelizedata.setName(sequelizedata.getName());
                }

                return existingSequelizedata;
            })
            .map(sequelizedataRepository::save);
    }

    /**
     * Get all the sequelizedata.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Sequelizedata> findAll(Pageable pageable) {
        log.debug("Request to get all Sequelizedata");
        return sequelizedataRepository.findAll(pageable);
    }

    /**
     * Get one sequelizedata by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Sequelizedata> findOne(Long id) {
        log.debug("Request to get Sequelizedata : {}", id);
        return sequelizedataRepository.findById(id);
    }

    /**
     * Delete the sequelizedata by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Sequelizedata : {}", id);
        sequelizedataRepository.deleteById(id);
    }
}
