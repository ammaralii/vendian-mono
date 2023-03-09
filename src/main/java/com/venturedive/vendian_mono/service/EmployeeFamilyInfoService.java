package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.EmployeeFamilyInfo;
import com.venturedive.vendian_mono.repository.EmployeeFamilyInfoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmployeeFamilyInfo}.
 */
@Service
@Transactional
public class EmployeeFamilyInfoService {

    private final Logger log = LoggerFactory.getLogger(EmployeeFamilyInfoService.class);

    private final EmployeeFamilyInfoRepository employeeFamilyInfoRepository;

    public EmployeeFamilyInfoService(EmployeeFamilyInfoRepository employeeFamilyInfoRepository) {
        this.employeeFamilyInfoRepository = employeeFamilyInfoRepository;
    }

    /**
     * Save a employeeFamilyInfo.
     *
     * @param employeeFamilyInfo the entity to save.
     * @return the persisted entity.
     */
    public EmployeeFamilyInfo save(EmployeeFamilyInfo employeeFamilyInfo) {
        log.debug("Request to save EmployeeFamilyInfo : {}", employeeFamilyInfo);
        return employeeFamilyInfoRepository.save(employeeFamilyInfo);
    }

    /**
     * Update a employeeFamilyInfo.
     *
     * @param employeeFamilyInfo the entity to save.
     * @return the persisted entity.
     */
    public EmployeeFamilyInfo update(EmployeeFamilyInfo employeeFamilyInfo) {
        log.debug("Request to update EmployeeFamilyInfo : {}", employeeFamilyInfo);
        return employeeFamilyInfoRepository.save(employeeFamilyInfo);
    }

    /**
     * Partially update a employeeFamilyInfo.
     *
     * @param employeeFamilyInfo the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeFamilyInfo> partialUpdate(EmployeeFamilyInfo employeeFamilyInfo) {
        log.debug("Request to partially update EmployeeFamilyInfo : {}", employeeFamilyInfo);

        return employeeFamilyInfoRepository
            .findById(employeeFamilyInfo.getId())
            .map(existingEmployeeFamilyInfo -> {
                if (employeeFamilyInfo.getFullname() != null) {
                    existingEmployeeFamilyInfo.setFullname(employeeFamilyInfo.getFullname());
                }
                if (employeeFamilyInfo.getRelationship() != null) {
                    existingEmployeeFamilyInfo.setRelationship(employeeFamilyInfo.getRelationship());
                }
                if (employeeFamilyInfo.getContactno() != null) {
                    existingEmployeeFamilyInfo.setContactno(employeeFamilyInfo.getContactno());
                }
                if (employeeFamilyInfo.getEmail() != null) {
                    existingEmployeeFamilyInfo.setEmail(employeeFamilyInfo.getEmail());
                }
                if (employeeFamilyInfo.getDob() != null) {
                    existingEmployeeFamilyInfo.setDob(employeeFamilyInfo.getDob());
                }
                if (employeeFamilyInfo.getRegisteredinmedical() != null) {
                    existingEmployeeFamilyInfo.setRegisteredinmedical(employeeFamilyInfo.getRegisteredinmedical());
                }
                if (employeeFamilyInfo.getCnic() != null) {
                    existingEmployeeFamilyInfo.setCnic(employeeFamilyInfo.getCnic());
                }
                if (employeeFamilyInfo.getCnicContentType() != null) {
                    existingEmployeeFamilyInfo.setCnicContentType(employeeFamilyInfo.getCnicContentType());
                }
                if (employeeFamilyInfo.getCreatedat() != null) {
                    existingEmployeeFamilyInfo.setCreatedat(employeeFamilyInfo.getCreatedat());
                }
                if (employeeFamilyInfo.getUpdatedat() != null) {
                    existingEmployeeFamilyInfo.setUpdatedat(employeeFamilyInfo.getUpdatedat());
                }
                if (employeeFamilyInfo.getMedicalpolicyno() != null) {
                    existingEmployeeFamilyInfo.setMedicalpolicyno(employeeFamilyInfo.getMedicalpolicyno());
                }
                if (employeeFamilyInfo.getGender() != null) {
                    existingEmployeeFamilyInfo.setGender(employeeFamilyInfo.getGender());
                }

                return existingEmployeeFamilyInfo;
            })
            .map(employeeFamilyInfoRepository::save);
    }

    /**
     * Get all the employeeFamilyInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeFamilyInfo> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeFamilyInfos");
        return employeeFamilyInfoRepository.findAll(pageable);
    }

    /**
     * Get one employeeFamilyInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeFamilyInfo> findOne(Long id) {
        log.debug("Request to get EmployeeFamilyInfo : {}", id);
        return employeeFamilyInfoRepository.findById(id);
    }

    /**
     * Delete the employeeFamilyInfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeFamilyInfo : {}", id);
        employeeFamilyInfoRepository.deleteById(id);
    }
}
