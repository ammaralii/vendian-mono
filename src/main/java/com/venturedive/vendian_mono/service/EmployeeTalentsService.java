package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.EmployeeTalents;
import com.venturedive.vendian_mono.repository.EmployeeTalentsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmployeeTalents}.
 */
@Service
@Transactional
public class EmployeeTalentsService {

    private final Logger log = LoggerFactory.getLogger(EmployeeTalentsService.class);

    private final EmployeeTalentsRepository employeeTalentsRepository;

    public EmployeeTalentsService(EmployeeTalentsRepository employeeTalentsRepository) {
        this.employeeTalentsRepository = employeeTalentsRepository;
    }

    /**
     * Save a employeeTalents.
     *
     * @param employeeTalents the entity to save.
     * @return the persisted entity.
     */
    public EmployeeTalents save(EmployeeTalents employeeTalents) {
        log.debug("Request to save EmployeeTalents : {}", employeeTalents);
        return employeeTalentsRepository.save(employeeTalents);
    }

    /**
     * Update a employeeTalents.
     *
     * @param employeeTalents the entity to save.
     * @return the persisted entity.
     */
    public EmployeeTalents update(EmployeeTalents employeeTalents) {
        log.debug("Request to update EmployeeTalents : {}", employeeTalents);
        return employeeTalentsRepository.save(employeeTalents);
    }

    /**
     * Partially update a employeeTalents.
     *
     * @param employeeTalents the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeTalents> partialUpdate(EmployeeTalents employeeTalents) {
        log.debug("Request to partially update EmployeeTalents : {}", employeeTalents);

        return employeeTalentsRepository
            .findById(employeeTalents.getId())
            .map(existingEmployeeTalents -> {
                if (employeeTalents.getCriticalposition() != null) {
                    existingEmployeeTalents.setCriticalposition(employeeTalents.getCriticalposition());
                }
                if (employeeTalents.getHighpotential() != null) {
                    existingEmployeeTalents.setHighpotential(employeeTalents.getHighpotential());
                }
                if (employeeTalents.getSuccessorfor() != null) {
                    existingEmployeeTalents.setSuccessorfor(employeeTalents.getSuccessorfor());
                }
                if (employeeTalents.getCriticalexperience() != null) {
                    existingEmployeeTalents.setCriticalexperience(employeeTalents.getCriticalexperience());
                }
                if (employeeTalents.getPromotionreadiness() != null) {
                    existingEmployeeTalents.setPromotionreadiness(employeeTalents.getPromotionreadiness());
                }
                if (employeeTalents.getLeadershipqualities() != null) {
                    existingEmployeeTalents.setLeadershipqualities(employeeTalents.getLeadershipqualities());
                }
                if (employeeTalents.getCareerambitions() != null) {
                    existingEmployeeTalents.setCareerambitions(employeeTalents.getCareerambitions());
                }
                if (employeeTalents.getCreatedat() != null) {
                    existingEmployeeTalents.setCreatedat(employeeTalents.getCreatedat());
                }
                if (employeeTalents.getUpdatedat() != null) {
                    existingEmployeeTalents.setUpdatedat(employeeTalents.getUpdatedat());
                }

                return existingEmployeeTalents;
            })
            .map(employeeTalentsRepository::save);
    }

    /**
     * Get all the employeeTalents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeTalents> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeTalents");
        return employeeTalentsRepository.findAll(pageable);
    }

    /**
     * Get one employeeTalents by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeTalents> findOne(Long id) {
        log.debug("Request to get EmployeeTalents : {}", id);
        return employeeTalentsRepository.findById(id);
    }

    /**
     * Delete the employeeTalents by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeTalents : {}", id);
        employeeTalentsRepository.deleteById(id);
    }
}
