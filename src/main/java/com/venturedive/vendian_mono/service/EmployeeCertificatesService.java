package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.EmployeeCertificates;
import com.venturedive.vendian_mono.repository.EmployeeCertificatesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmployeeCertificates}.
 */
@Service
@Transactional
public class EmployeeCertificatesService {

    private final Logger log = LoggerFactory.getLogger(EmployeeCertificatesService.class);

    private final EmployeeCertificatesRepository employeeCertificatesRepository;

    public EmployeeCertificatesService(EmployeeCertificatesRepository employeeCertificatesRepository) {
        this.employeeCertificatesRepository = employeeCertificatesRepository;
    }

    /**
     * Save a employeeCertificates.
     *
     * @param employeeCertificates the entity to save.
     * @return the persisted entity.
     */
    public EmployeeCertificates save(EmployeeCertificates employeeCertificates) {
        log.debug("Request to save EmployeeCertificates : {}", employeeCertificates);
        return employeeCertificatesRepository.save(employeeCertificates);
    }

    /**
     * Update a employeeCertificates.
     *
     * @param employeeCertificates the entity to save.
     * @return the persisted entity.
     */
    public EmployeeCertificates update(EmployeeCertificates employeeCertificates) {
        log.debug("Request to update EmployeeCertificates : {}", employeeCertificates);
        return employeeCertificatesRepository.save(employeeCertificates);
    }

    /**
     * Partially update a employeeCertificates.
     *
     * @param employeeCertificates the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeCertificates> partialUpdate(EmployeeCertificates employeeCertificates) {
        log.debug("Request to partially update EmployeeCertificates : {}", employeeCertificates);

        return employeeCertificatesRepository
            .findById(employeeCertificates.getId())
            .map(existingEmployeeCertificates -> {
                if (employeeCertificates.getName() != null) {
                    existingEmployeeCertificates.setName(employeeCertificates.getName());
                }
                if (employeeCertificates.getCertificateno() != null) {
                    existingEmployeeCertificates.setCertificateno(employeeCertificates.getCertificateno());
                }
                if (employeeCertificates.getIssuingbody() != null) {
                    existingEmployeeCertificates.setIssuingbody(employeeCertificates.getIssuingbody());
                }
                if (employeeCertificates.getDate() != null) {
                    existingEmployeeCertificates.setDate(employeeCertificates.getDate());
                }
                if (employeeCertificates.getCreatedat() != null) {
                    existingEmployeeCertificates.setCreatedat(employeeCertificates.getCreatedat());
                }
                if (employeeCertificates.getUpdatedat() != null) {
                    existingEmployeeCertificates.setUpdatedat(employeeCertificates.getUpdatedat());
                }
                if (employeeCertificates.getValidtill() != null) {
                    existingEmployeeCertificates.setValidtill(employeeCertificates.getValidtill());
                }
                if (employeeCertificates.getCertificatecompetency() != null) {
                    existingEmployeeCertificates.setCertificatecompetency(employeeCertificates.getCertificatecompetency());
                }
                if (employeeCertificates.getDeletedat() != null) {
                    existingEmployeeCertificates.setDeletedat(employeeCertificates.getDeletedat());
                }

                return existingEmployeeCertificates;
            })
            .map(employeeCertificatesRepository::save);
    }

    /**
     * Get all the employeeCertificates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeCertificates> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeCertificates");
        return employeeCertificatesRepository.findAll(pageable);
    }

    /**
     * Get one employeeCertificates by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeCertificates> findOne(Long id) {
        log.debug("Request to get EmployeeCertificates : {}", id);
        return employeeCertificatesRepository.findById(id);
    }

    /**
     * Delete the employeeCertificates by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeCertificates : {}", id);
        employeeCertificatesRepository.deleteById(id);
    }
}
