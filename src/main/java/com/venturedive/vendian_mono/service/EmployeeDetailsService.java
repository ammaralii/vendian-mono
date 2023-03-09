package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.EmployeeDetails;
import com.venturedive.vendian_mono.repository.EmployeeDetailsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmployeeDetails}.
 */
@Service
@Transactional
public class EmployeeDetailsService {

    private final Logger log = LoggerFactory.getLogger(EmployeeDetailsService.class);

    private final EmployeeDetailsRepository employeeDetailsRepository;

    public EmployeeDetailsService(EmployeeDetailsRepository employeeDetailsRepository) {
        this.employeeDetailsRepository = employeeDetailsRepository;
    }

    /**
     * Save a employeeDetails.
     *
     * @param employeeDetails the entity to save.
     * @return the persisted entity.
     */
    public EmployeeDetails save(EmployeeDetails employeeDetails) {
        log.debug("Request to save EmployeeDetails : {}", employeeDetails);
        return employeeDetailsRepository.save(employeeDetails);
    }

    /**
     * Update a employeeDetails.
     *
     * @param employeeDetails the entity to save.
     * @return the persisted entity.
     */
    public EmployeeDetails update(EmployeeDetails employeeDetails) {
        log.debug("Request to update EmployeeDetails : {}", employeeDetails);
        return employeeDetailsRepository.save(employeeDetails);
    }

    /**
     * Partially update a employeeDetails.
     *
     * @param employeeDetails the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeDetails> partialUpdate(EmployeeDetails employeeDetails) {
        log.debug("Request to partially update EmployeeDetails : {}", employeeDetails);

        return employeeDetailsRepository
            .findById(employeeDetails.getId())
            .map(existingEmployeeDetails -> {
                if (employeeDetails.getReligion() != null) {
                    existingEmployeeDetails.setReligion(employeeDetails.getReligion());
                }
                if (employeeDetails.getMaritalstatus() != null) {
                    existingEmployeeDetails.setMaritalstatus(employeeDetails.getMaritalstatus());
                }
                if (employeeDetails.getCnic() != null) {
                    existingEmployeeDetails.setCnic(employeeDetails.getCnic());
                }
                if (employeeDetails.getCnicContentType() != null) {
                    existingEmployeeDetails.setCnicContentType(employeeDetails.getCnicContentType());
                }
                if (employeeDetails.getCnicexpiry() != null) {
                    existingEmployeeDetails.setCnicexpiry(employeeDetails.getCnicexpiry());
                }
                if (employeeDetails.getCnicexpiryContentType() != null) {
                    existingEmployeeDetails.setCnicexpiryContentType(employeeDetails.getCnicexpiryContentType());
                }
                if (employeeDetails.getBloodgroup() != null) {
                    existingEmployeeDetails.setBloodgroup(employeeDetails.getBloodgroup());
                }
                if (employeeDetails.getTaxreturnfiler() != null) {
                    existingEmployeeDetails.setTaxreturnfiler(employeeDetails.getTaxreturnfiler());
                }
                if (employeeDetails.getTaxreturnfilerContentType() != null) {
                    existingEmployeeDetails.setTaxreturnfilerContentType(employeeDetails.getTaxreturnfilerContentType());
                }
                if (employeeDetails.getPassportno() != null) {
                    existingEmployeeDetails.setPassportno(employeeDetails.getPassportno());
                }
                if (employeeDetails.getPassportnoContentType() != null) {
                    existingEmployeeDetails.setPassportnoContentType(employeeDetails.getPassportnoContentType());
                }
                if (employeeDetails.getPassportexpiry() != null) {
                    existingEmployeeDetails.setPassportexpiry(employeeDetails.getPassportexpiry());
                }
                if (employeeDetails.getPassportexpiryContentType() != null) {
                    existingEmployeeDetails.setPassportexpiryContentType(employeeDetails.getPassportexpiryContentType());
                }
                if (employeeDetails.getCreatedat() != null) {
                    existingEmployeeDetails.setCreatedat(employeeDetails.getCreatedat());
                }
                if (employeeDetails.getUpdatedat() != null) {
                    existingEmployeeDetails.setUpdatedat(employeeDetails.getUpdatedat());
                }
                if (employeeDetails.getTotaltenure() != null) {
                    existingEmployeeDetails.setTotaltenure(employeeDetails.getTotaltenure());
                }

                return existingEmployeeDetails;
            })
            .map(employeeDetailsRepository::save);
    }

    /**
     * Get all the employeeDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeDetails> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeDetails");
        return employeeDetailsRepository.findAll(pageable);
    }

    /**
     * Get one employeeDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeDetails> findOne(Long id) {
        log.debug("Request to get EmployeeDetails : {}", id);
        return employeeDetailsRepository.findById(id);
    }

    /**
     * Delete the employeeDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeDetails : {}", id);
        employeeDetailsRepository.deleteById(id);
    }
}
