package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.EmployeeJobInfo;
import com.venturedive.vendian_mono.repository.EmployeeJobInfoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmployeeJobInfo}.
 */
@Service
@Transactional
public class EmployeeJobInfoService {

    private final Logger log = LoggerFactory.getLogger(EmployeeJobInfoService.class);

    private final EmployeeJobInfoRepository employeeJobInfoRepository;

    public EmployeeJobInfoService(EmployeeJobInfoRepository employeeJobInfoRepository) {
        this.employeeJobInfoRepository = employeeJobInfoRepository;
    }

    /**
     * Save a employeeJobInfo.
     *
     * @param employeeJobInfo the entity to save.
     * @return the persisted entity.
     */
    public EmployeeJobInfo save(EmployeeJobInfo employeeJobInfo) {
        log.debug("Request to save EmployeeJobInfo : {}", employeeJobInfo);
        return employeeJobInfoRepository.save(employeeJobInfo);
    }

    /**
     * Update a employeeJobInfo.
     *
     * @param employeeJobInfo the entity to save.
     * @return the persisted entity.
     */
    public EmployeeJobInfo update(EmployeeJobInfo employeeJobInfo) {
        log.debug("Request to update EmployeeJobInfo : {}", employeeJobInfo);
        return employeeJobInfoRepository.save(employeeJobInfo);
    }

    /**
     * Partially update a employeeJobInfo.
     *
     * @param employeeJobInfo the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeJobInfo> partialUpdate(EmployeeJobInfo employeeJobInfo) {
        log.debug("Request to partially update EmployeeJobInfo : {}", employeeJobInfo);

        return employeeJobInfoRepository
            .findById(employeeJobInfo.getId())
            .map(existingEmployeeJobInfo -> {
                if (employeeJobInfo.getTitle() != null) {
                    existingEmployeeJobInfo.setTitle(employeeJobInfo.getTitle());
                }
                if (employeeJobInfo.getGrade() != null) {
                    existingEmployeeJobInfo.setGrade(employeeJobInfo.getGrade());
                }
                if (employeeJobInfo.getSubgrade() != null) {
                    existingEmployeeJobInfo.setSubgrade(employeeJobInfo.getSubgrade());
                }
                if (employeeJobInfo.getStartdate() != null) {
                    existingEmployeeJobInfo.setStartdate(employeeJobInfo.getStartdate());
                }
                if (employeeJobInfo.getEnddate() != null) {
                    existingEmployeeJobInfo.setEnddate(employeeJobInfo.getEnddate());
                }
                if (employeeJobInfo.getCreatedat() != null) {
                    existingEmployeeJobInfo.setCreatedat(employeeJobInfo.getCreatedat());
                }
                if (employeeJobInfo.getUpdatedat() != null) {
                    existingEmployeeJobInfo.setUpdatedat(employeeJobInfo.getUpdatedat());
                }
                if (employeeJobInfo.getLocation() != null) {
                    existingEmployeeJobInfo.setLocation(employeeJobInfo.getLocation());
                }
                if (employeeJobInfo.getGrosssalary() != null) {
                    existingEmployeeJobInfo.setGrosssalary(employeeJobInfo.getGrosssalary());
                }
                if (employeeJobInfo.getGrosssalaryContentType() != null) {
                    existingEmployeeJobInfo.setGrosssalaryContentType(employeeJobInfo.getGrosssalaryContentType());
                }
                if (employeeJobInfo.getFuelallowance() != null) {
                    existingEmployeeJobInfo.setFuelallowance(employeeJobInfo.getFuelallowance());
                }
                if (employeeJobInfo.getFuelallowanceContentType() != null) {
                    existingEmployeeJobInfo.setFuelallowanceContentType(employeeJobInfo.getFuelallowanceContentType());
                }

                return existingEmployeeJobInfo;
            })
            .map(employeeJobInfoRepository::save);
    }

    /**
     * Get all the employeeJobInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeJobInfo> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeJobInfos");
        return employeeJobInfoRepository.findAll(pageable);
    }

    /**
     * Get all the employeeJobInfos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<EmployeeJobInfo> findAllWithEagerRelationships(Pageable pageable) {
        return employeeJobInfoRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one employeeJobInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeJobInfo> findOne(Long id) {
        log.debug("Request to get EmployeeJobInfo : {}", id);
        return employeeJobInfoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the employeeJobInfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeJobInfo : {}", id);
        employeeJobInfoRepository.deleteById(id);
    }
}
