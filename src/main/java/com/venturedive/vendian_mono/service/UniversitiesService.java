package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.Universities;
import com.venturedive.vendian_mono.repository.UniversitiesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Universities}.
 */
@Service
@Transactional
public class UniversitiesService {

    private final Logger log = LoggerFactory.getLogger(UniversitiesService.class);

    private final UniversitiesRepository universitiesRepository;

    public UniversitiesService(UniversitiesRepository universitiesRepository) {
        this.universitiesRepository = universitiesRepository;
    }

    /**
     * Save a universities.
     *
     * @param universities the entity to save.
     * @return the persisted entity.
     */
    public Universities save(Universities universities) {
        log.debug("Request to save Universities : {}", universities);
        return universitiesRepository.save(universities);
    }

    /**
     * Update a universities.
     *
     * @param universities the entity to save.
     * @return the persisted entity.
     */
    public Universities update(Universities universities) {
        log.debug("Request to update Universities : {}", universities);
        return universitiesRepository.save(universities);
    }

    /**
     * Partially update a universities.
     *
     * @param universities the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Universities> partialUpdate(Universities universities) {
        log.debug("Request to partially update Universities : {}", universities);

        return universitiesRepository
            .findById(universities.getId())
            .map(existingUniversities -> {
                if (universities.getName() != null) {
                    existingUniversities.setName(universities.getName());
                }
                if (universities.getCreatedat() != null) {
                    existingUniversities.setCreatedat(universities.getCreatedat());
                }
                if (universities.getUpdatedat() != null) {
                    existingUniversities.setUpdatedat(universities.getUpdatedat());
                }

                return existingUniversities;
            })
            .map(universitiesRepository::save);
    }

    /**
     * Get all the universities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Universities> findAll(Pageable pageable) {
        log.debug("Request to get all Universities");
        return universitiesRepository.findAll(pageable);
    }

    /**
     * Get one universities by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Universities> findOne(Long id) {
        log.debug("Request to get Universities : {}", id);
        return universitiesRepository.findById(id);
    }

    /**
     * Delete the universities by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Universities : {}", id);
        universitiesRepository.deleteById(id);
    }
}
