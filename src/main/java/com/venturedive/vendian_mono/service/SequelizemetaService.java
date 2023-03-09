package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.Sequelizemeta;
import com.venturedive.vendian_mono.repository.SequelizemetaRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Sequelizemeta}.
 */
@Service
@Transactional
public class SequelizemetaService {

    private final Logger log = LoggerFactory.getLogger(SequelizemetaService.class);

    private final SequelizemetaRepository sequelizemetaRepository;

    public SequelizemetaService(SequelizemetaRepository sequelizemetaRepository) {
        this.sequelizemetaRepository = sequelizemetaRepository;
    }

    /**
     * Save a sequelizemeta.
     *
     * @param sequelizemeta the entity to save.
     * @return the persisted entity.
     */
    public Sequelizemeta save(Sequelizemeta sequelizemeta) {
        log.debug("Request to save Sequelizemeta : {}", sequelizemeta);
        return sequelizemetaRepository.save(sequelizemeta);
    }

    /**
     * Update a sequelizemeta.
     *
     * @param sequelizemeta the entity to save.
     * @return the persisted entity.
     */
    public Sequelizemeta update(Sequelizemeta sequelizemeta) {
        log.debug("Request to update Sequelizemeta : {}", sequelizemeta);
        return sequelizemetaRepository.save(sequelizemeta);
    }

    /**
     * Partially update a sequelizemeta.
     *
     * @param sequelizemeta the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Sequelizemeta> partialUpdate(Sequelizemeta sequelizemeta) {
        log.debug("Request to partially update Sequelizemeta : {}", sequelizemeta);

        return sequelizemetaRepository
            .findById(sequelizemeta.getId())
            .map(existingSequelizemeta -> {
                if (sequelizemeta.getName() != null) {
                    existingSequelizemeta.setName(sequelizemeta.getName());
                }

                return existingSequelizemeta;
            })
            .map(sequelizemetaRepository::save);
    }

    /**
     * Get all the sequelizemetas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Sequelizemeta> findAll(Pageable pageable) {
        log.debug("Request to get all Sequelizemetas");
        return sequelizemetaRepository.findAll(pageable);
    }

    /**
     * Get one sequelizemeta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Sequelizemeta> findOne(Long id) {
        log.debug("Request to get Sequelizemeta : {}", id);
        return sequelizemetaRepository.findById(id);
    }

    /**
     * Delete the sequelizemeta by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Sequelizemeta : {}", id);
        sequelizemetaRepository.deleteById(id);
    }
}
