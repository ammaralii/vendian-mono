package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.EmploymentHistory;
import com.venturedive.vendian_mono.repository.EmploymentHistoryRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmploymentHistory}.
 */
@Service
@Transactional
public class EmploymentHistoryService {

    private final Logger log = LoggerFactory.getLogger(EmploymentHistoryService.class);

    private final EmploymentHistoryRepository employmentHistoryRepository;

    public EmploymentHistoryService(EmploymentHistoryRepository employmentHistoryRepository) {
        this.employmentHistoryRepository = employmentHistoryRepository;
    }

    /**
     * Save a employmentHistory.
     *
     * @param employmentHistory the entity to save.
     * @return the persisted entity.
     */
    public EmploymentHistory save(EmploymentHistory employmentHistory) {
        log.debug("Request to save EmploymentHistory : {}", employmentHistory);
        return employmentHistoryRepository.save(employmentHistory);
    }

    /**
     * Update a employmentHistory.
     *
     * @param employmentHistory the entity to save.
     * @return the persisted entity.
     */
    public EmploymentHistory update(EmploymentHistory employmentHistory) {
        log.debug("Request to update EmploymentHistory : {}", employmentHistory);
        return employmentHistoryRepository.save(employmentHistory);
    }

    /**
     * Partially update a employmentHistory.
     *
     * @param employmentHistory the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmploymentHistory> partialUpdate(EmploymentHistory employmentHistory) {
        log.debug("Request to partially update EmploymentHistory : {}", employmentHistory);

        return employmentHistoryRepository
            .findById(employmentHistory.getId())
            .map(existingEmploymentHistory -> {
                if (employmentHistory.getPositiontitle() != null) {
                    existingEmploymentHistory.setPositiontitle(employmentHistory.getPositiontitle());
                }
                if (employmentHistory.getCompanyname() != null) {
                    existingEmploymentHistory.setCompanyname(employmentHistory.getCompanyname());
                }
                if (employmentHistory.getGrade() != null) {
                    existingEmploymentHistory.setGrade(employmentHistory.getGrade());
                }
                if (employmentHistory.getJobdescription() != null) {
                    existingEmploymentHistory.setJobdescription(employmentHistory.getJobdescription());
                }
                if (employmentHistory.getCity() != null) {
                    existingEmploymentHistory.setCity(employmentHistory.getCity());
                }
                if (employmentHistory.getCountry() != null) {
                    existingEmploymentHistory.setCountry(employmentHistory.getCountry());
                }
                if (employmentHistory.getStartdate() != null) {
                    existingEmploymentHistory.setStartdate(employmentHistory.getStartdate());
                }
                if (employmentHistory.getEnddate() != null) {
                    existingEmploymentHistory.setEnddate(employmentHistory.getEnddate());
                }
                if (employmentHistory.getCreatedat() != null) {
                    existingEmploymentHistory.setCreatedat(employmentHistory.getCreatedat());
                }
                if (employmentHistory.getUpdatedat() != null) {
                    existingEmploymentHistory.setUpdatedat(employmentHistory.getUpdatedat());
                }
                if (employmentHistory.getReasonforleaving() != null) {
                    existingEmploymentHistory.setReasonforleaving(employmentHistory.getReasonforleaving());
                }
                if (employmentHistory.getGrosssalary() != null) {
                    existingEmploymentHistory.setGrosssalary(employmentHistory.getGrosssalary());
                }
                if (employmentHistory.getGrosssalaryContentType() != null) {
                    existingEmploymentHistory.setGrosssalaryContentType(employmentHistory.getGrosssalaryContentType());
                }

                return existingEmploymentHistory;
            })
            .map(employmentHistoryRepository::save);
    }

    /**
     * Get all the employmentHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmploymentHistory> findAll(Pageable pageable) {
        log.debug("Request to get all EmploymentHistories");
        return employmentHistoryRepository.findAll(pageable);
    }

    /**
     * Get one employmentHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmploymentHistory> findOne(Long id) {
        log.debug("Request to get EmploymentHistory : {}", id);
        return employmentHistoryRepository.findById(id);
    }

    /**
     * Delete the employmentHistory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmploymentHistory : {}", id);
        employmentHistoryRepository.deleteById(id);
    }
}
