package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.PcRatingsTrainings;
import com.venturedive.vendian_mono.repository.PcRatingsTrainingsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PcRatingsTrainings}.
 */
@Service
@Transactional
public class PcRatingsTrainingsService {

    private final Logger log = LoggerFactory.getLogger(PcRatingsTrainingsService.class);

    private final PcRatingsTrainingsRepository pcRatingsTrainingsRepository;

    public PcRatingsTrainingsService(PcRatingsTrainingsRepository pcRatingsTrainingsRepository) {
        this.pcRatingsTrainingsRepository = pcRatingsTrainingsRepository;
    }

    /**
     * Save a pcRatingsTrainings.
     *
     * @param pcRatingsTrainings the entity to save.
     * @return the persisted entity.
     */
    public PcRatingsTrainings save(PcRatingsTrainings pcRatingsTrainings) {
        log.debug("Request to save PcRatingsTrainings : {}", pcRatingsTrainings);
        return pcRatingsTrainingsRepository.save(pcRatingsTrainings);
    }

    /**
     * Update a pcRatingsTrainings.
     *
     * @param pcRatingsTrainings the entity to save.
     * @return the persisted entity.
     */
    public PcRatingsTrainings update(PcRatingsTrainings pcRatingsTrainings) {
        log.debug("Request to update PcRatingsTrainings : {}", pcRatingsTrainings);
        return pcRatingsTrainingsRepository.save(pcRatingsTrainings);
    }

    /**
     * Partially update a pcRatingsTrainings.
     *
     * @param pcRatingsTrainings the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PcRatingsTrainings> partialUpdate(PcRatingsTrainings pcRatingsTrainings) {
        log.debug("Request to partially update PcRatingsTrainings : {}", pcRatingsTrainings);

        return pcRatingsTrainingsRepository
            .findById(pcRatingsTrainings.getId())
            .map(existingPcRatingsTrainings -> {
                if (pcRatingsTrainings.getStrength() != null) {
                    existingPcRatingsTrainings.setStrength(pcRatingsTrainings.getStrength());
                }
                if (pcRatingsTrainings.getDevelopmentArea() != null) {
                    existingPcRatingsTrainings.setDevelopmentArea(pcRatingsTrainings.getDevelopmentArea());
                }
                if (pcRatingsTrainings.getCareerAmbition() != null) {
                    existingPcRatingsTrainings.setCareerAmbition(pcRatingsTrainings.getCareerAmbition());
                }
                if (pcRatingsTrainings.getShortCourse() != null) {
                    existingPcRatingsTrainings.setShortCourse(pcRatingsTrainings.getShortCourse());
                }
                if (pcRatingsTrainings.getTechnicalTraining() != null) {
                    existingPcRatingsTrainings.setTechnicalTraining(pcRatingsTrainings.getTechnicalTraining());
                }
                if (pcRatingsTrainings.getSoftSkillsTraining() != null) {
                    existingPcRatingsTrainings.setSoftSkillsTraining(pcRatingsTrainings.getSoftSkillsTraining());
                }
                if (pcRatingsTrainings.getCriticalPosition() != null) {
                    existingPcRatingsTrainings.setCriticalPosition(pcRatingsTrainings.getCriticalPosition());
                }
                if (pcRatingsTrainings.getHighPotential() != null) {
                    existingPcRatingsTrainings.setHighPotential(pcRatingsTrainings.getHighPotential());
                }
                if (pcRatingsTrainings.getCreatedAt() != null) {
                    existingPcRatingsTrainings.setCreatedAt(pcRatingsTrainings.getCreatedAt());
                }
                if (pcRatingsTrainings.getUpdatedAt() != null) {
                    existingPcRatingsTrainings.setUpdatedAt(pcRatingsTrainings.getUpdatedAt());
                }
                if (pcRatingsTrainings.getDeletedAt() != null) {
                    existingPcRatingsTrainings.setDeletedAt(pcRatingsTrainings.getDeletedAt());
                }
                if (pcRatingsTrainings.getVersion() != null) {
                    existingPcRatingsTrainings.setVersion(pcRatingsTrainings.getVersion());
                }

                return existingPcRatingsTrainings;
            })
            .map(pcRatingsTrainingsRepository::save);
    }

    /**
     * Get all the pcRatingsTrainings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PcRatingsTrainings> findAll(Pageable pageable) {
        log.debug("Request to get all PcRatingsTrainings");
        return pcRatingsTrainingsRepository.findAll(pageable);
    }

    /**
     * Get one pcRatingsTrainings by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PcRatingsTrainings> findOne(Long id) {
        log.debug("Request to get PcRatingsTrainings : {}", id);
        return pcRatingsTrainingsRepository.findById(id);
    }

    /**
     * Delete the pcRatingsTrainings by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PcRatingsTrainings : {}", id);
        pcRatingsTrainingsRepository.deleteById(id);
    }
}
