package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.EmployeeComments;
import com.venturedive.vendian_mono.repository.EmployeeCommentsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmployeeComments}.
 */
@Service
@Transactional
public class EmployeeCommentsService {

    private final Logger log = LoggerFactory.getLogger(EmployeeCommentsService.class);

    private final EmployeeCommentsRepository employeeCommentsRepository;

    public EmployeeCommentsService(EmployeeCommentsRepository employeeCommentsRepository) {
        this.employeeCommentsRepository = employeeCommentsRepository;
    }

    /**
     * Save a employeeComments.
     *
     * @param employeeComments the entity to save.
     * @return the persisted entity.
     */
    public EmployeeComments save(EmployeeComments employeeComments) {
        log.debug("Request to save EmployeeComments : {}", employeeComments);
        return employeeCommentsRepository.save(employeeComments);
    }

    /**
     * Update a employeeComments.
     *
     * @param employeeComments the entity to save.
     * @return the persisted entity.
     */
    public EmployeeComments update(EmployeeComments employeeComments) {
        log.debug("Request to update EmployeeComments : {}", employeeComments);
        return employeeCommentsRepository.save(employeeComments);
    }

    /**
     * Partially update a employeeComments.
     *
     * @param employeeComments the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeComments> partialUpdate(EmployeeComments employeeComments) {
        log.debug("Request to partially update EmployeeComments : {}", employeeComments);

        return employeeCommentsRepository
            .findById(employeeComments.getId())
            .map(existingEmployeeComments -> {
                if (employeeComments.getTitle() != null) {
                    existingEmployeeComments.setTitle(employeeComments.getTitle());
                }
                if (employeeComments.getTitleContentType() != null) {
                    existingEmployeeComments.setTitleContentType(employeeComments.getTitleContentType());
                }
                if (employeeComments.getContent() != null) {
                    existingEmployeeComments.setContent(employeeComments.getContent());
                }
                if (employeeComments.getContentContentType() != null) {
                    existingEmployeeComments.setContentContentType(employeeComments.getContentContentType());
                }
                if (employeeComments.getDated() != null) {
                    existingEmployeeComments.setDated(employeeComments.getDated());
                }
                if (employeeComments.getDatedContentType() != null) {
                    existingEmployeeComments.setDatedContentType(employeeComments.getDatedContentType());
                }
                if (employeeComments.getCreatedat() != null) {
                    existingEmployeeComments.setCreatedat(employeeComments.getCreatedat());
                }
                if (employeeComments.getUpdatedat() != null) {
                    existingEmployeeComments.setUpdatedat(employeeComments.getUpdatedat());
                }

                return existingEmployeeComments;
            })
            .map(employeeCommentsRepository::save);
    }

    /**
     * Get all the employeeComments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeComments> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeComments");
        return employeeCommentsRepository.findAll(pageable);
    }

    /**
     * Get one employeeComments by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeComments> findOne(Long id) {
        log.debug("Request to get EmployeeComments : {}", id);
        return employeeCommentsRepository.findById(id);
    }

    /**
     * Delete the employeeComments by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeComments : {}", id);
        employeeCommentsRepository.deleteById(id);
    }
}
