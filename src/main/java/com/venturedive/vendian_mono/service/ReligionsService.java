package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.Religions;
import com.venturedive.vendian_mono.repository.ReligionsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Religions}.
 */
@Service
@Transactional
public class ReligionsService {

    private final Logger log = LoggerFactory.getLogger(ReligionsService.class);

    private final ReligionsRepository religionsRepository;

    public ReligionsService(ReligionsRepository religionsRepository) {
        this.religionsRepository = religionsRepository;
    }

    /**
     * Save a religions.
     *
     * @param religions the entity to save.
     * @return the persisted entity.
     */
    public Religions save(Religions religions) {
        log.debug("Request to save Religions : {}", religions);
        return religionsRepository.save(religions);
    }

    /**
     * Update a religions.
     *
     * @param religions the entity to save.
     * @return the persisted entity.
     */
    public Religions update(Religions religions) {
        log.debug("Request to update Religions : {}", religions);
        return religionsRepository.save(religions);
    }

    /**
     * Partially update a religions.
     *
     * @param religions the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Religions> partialUpdate(Religions religions) {
        log.debug("Request to partially update Religions : {}", religions);

        return religionsRepository
            .findById(religions.getId())
            .map(existingReligions -> {
                if (religions.getName() != null) {
                    existingReligions.setName(religions.getName());
                }
                if (religions.getCreatedat() != null) {
                    existingReligions.setCreatedat(religions.getCreatedat());
                }
                if (religions.getUpdatedat() != null) {
                    existingReligions.setUpdatedat(religions.getUpdatedat());
                }

                return existingReligions;
            })
            .map(religionsRepository::save);
    }

    /**
     * Get all the religions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Religions> findAll(Pageable pageable) {
        log.debug("Request to get all Religions");
        return religionsRepository.findAll(pageable);
    }

    /**
     * Get one religions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Religions> findOne(Long id) {
        log.debug("Request to get Religions : {}", id);
        return religionsRepository.findById(id);
    }

    /**
     * Delete the religions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Religions : {}", id);
        religionsRepository.deleteById(id);
    }
}
