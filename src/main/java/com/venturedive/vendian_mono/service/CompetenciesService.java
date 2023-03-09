package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.Competencies;
import com.venturedive.vendian_mono.repository.CompetenciesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Competencies}.
 */
@Service
@Transactional
public class CompetenciesService {

    private final Logger log = LoggerFactory.getLogger(CompetenciesService.class);

    private final CompetenciesRepository competenciesRepository;

    public CompetenciesService(CompetenciesRepository competenciesRepository) {
        this.competenciesRepository = competenciesRepository;
    }

    /**
     * Save a competencies.
     *
     * @param competencies the entity to save.
     * @return the persisted entity.
     */
    public Competencies save(Competencies competencies) {
        log.debug("Request to save Competencies : {}", competencies);
        return competenciesRepository.save(competencies);
    }

    /**
     * Update a competencies.
     *
     * @param competencies the entity to save.
     * @return the persisted entity.
     */
    public Competencies update(Competencies competencies) {
        log.debug("Request to update Competencies : {}", competencies);
        return competenciesRepository.save(competencies);
    }

    /**
     * Partially update a competencies.
     *
     * @param competencies the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Competencies> partialUpdate(Competencies competencies) {
        log.debug("Request to partially update Competencies : {}", competencies);

        return competenciesRepository
            .findById(competencies.getId())
            .map(existingCompetencies -> {
                if (competencies.getName() != null) {
                    existingCompetencies.setName(competencies.getName());
                }
                if (competencies.getCreatedat() != null) {
                    existingCompetencies.setCreatedat(competencies.getCreatedat());
                }
                if (competencies.getUpdatedat() != null) {
                    existingCompetencies.setUpdatedat(competencies.getUpdatedat());
                }

                return existingCompetencies;
            })
            .map(competenciesRepository::save);
    }

    /**
     * Get all the competencies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Competencies> findAll(Pageable pageable) {
        log.debug("Request to get all Competencies");
        return competenciesRepository.findAll(pageable);
    }

    /**
     * Get one competencies by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Competencies> findOne(Long id) {
        log.debug("Request to get Competencies : {}", id);
        return competenciesRepository.findById(id);
    }

    /**
     * Delete the competencies by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Competencies : {}", id);
        competenciesRepository.deleteById(id);
    }
}
