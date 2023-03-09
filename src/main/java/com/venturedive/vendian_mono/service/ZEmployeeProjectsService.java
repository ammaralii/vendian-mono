package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.ZEmployeeProjects;
import com.venturedive.vendian_mono.repository.ZEmployeeProjectsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ZEmployeeProjects}.
 */
@Service
@Transactional
public class ZEmployeeProjectsService {

    private final Logger log = LoggerFactory.getLogger(ZEmployeeProjectsService.class);

    private final ZEmployeeProjectsRepository zEmployeeProjectsRepository;

    public ZEmployeeProjectsService(ZEmployeeProjectsRepository zEmployeeProjectsRepository) {
        this.zEmployeeProjectsRepository = zEmployeeProjectsRepository;
    }

    /**
     * Save a zEmployeeProjects.
     *
     * @param zEmployeeProjects the entity to save.
     * @return the persisted entity.
     */
    public ZEmployeeProjects save(ZEmployeeProjects zEmployeeProjects) {
        log.debug("Request to save ZEmployeeProjects : {}", zEmployeeProjects);
        return zEmployeeProjectsRepository.save(zEmployeeProjects);
    }

    /**
     * Update a zEmployeeProjects.
     *
     * @param zEmployeeProjects the entity to save.
     * @return the persisted entity.
     */
    public ZEmployeeProjects update(ZEmployeeProjects zEmployeeProjects) {
        log.debug("Request to update ZEmployeeProjects : {}", zEmployeeProjects);
        return zEmployeeProjectsRepository.save(zEmployeeProjects);
    }

    /**
     * Partially update a zEmployeeProjects.
     *
     * @param zEmployeeProjects the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ZEmployeeProjects> partialUpdate(ZEmployeeProjects zEmployeeProjects) {
        log.debug("Request to partially update ZEmployeeProjects : {}", zEmployeeProjects);

        return zEmployeeProjectsRepository
            .findById(zEmployeeProjects.getId())
            .map(existingZEmployeeProjects -> {
                if (zEmployeeProjects.getStatus() != null) {
                    existingZEmployeeProjects.setStatus(zEmployeeProjects.getStatus());
                }
                if (zEmployeeProjects.getType() != null) {
                    existingZEmployeeProjects.setType(zEmployeeProjects.getType());
                }
                if (zEmployeeProjects.getStartdate() != null) {
                    existingZEmployeeProjects.setStartdate(zEmployeeProjects.getStartdate());
                }
                if (zEmployeeProjects.getEnddate() != null) {
                    existingZEmployeeProjects.setEnddate(zEmployeeProjects.getEnddate());
                }
                if (zEmployeeProjects.getName() != null) {
                    existingZEmployeeProjects.setName(zEmployeeProjects.getName());
                }
                if (zEmployeeProjects.getAllocation() != null) {
                    existingZEmployeeProjects.setAllocation(zEmployeeProjects.getAllocation());
                }
                if (zEmployeeProjects.getBilled() != null) {
                    existingZEmployeeProjects.setBilled(zEmployeeProjects.getBilled());
                }
                if (zEmployeeProjects.getCreatedat() != null) {
                    existingZEmployeeProjects.setCreatedat(zEmployeeProjects.getCreatedat());
                }
                if (zEmployeeProjects.getUpdatedat() != null) {
                    existingZEmployeeProjects.setUpdatedat(zEmployeeProjects.getUpdatedat());
                }
                if (zEmployeeProjects.getDeliverymanagerid() != null) {
                    existingZEmployeeProjects.setDeliverymanagerid(zEmployeeProjects.getDeliverymanagerid());
                }
                if (zEmployeeProjects.getArchitectid() != null) {
                    existingZEmployeeProjects.setArchitectid(zEmployeeProjects.getArchitectid());
                }
                if (zEmployeeProjects.getNotes() != null) {
                    existingZEmployeeProjects.setNotes(zEmployeeProjects.getNotes());
                }
                if (zEmployeeProjects.getPreviousenddate() != null) {
                    existingZEmployeeProjects.setPreviousenddate(zEmployeeProjects.getPreviousenddate());
                }
                if (zEmployeeProjects.getData() != null) {
                    existingZEmployeeProjects.setData(zEmployeeProjects.getData());
                }
                if (zEmployeeProjects.getExtendedenddate() != null) {
                    existingZEmployeeProjects.setExtendedenddate(zEmployeeProjects.getExtendedenddate());
                }
                if (zEmployeeProjects.getProbability() != null) {
                    existingZEmployeeProjects.setProbability(zEmployeeProjects.getProbability());
                }

                return existingZEmployeeProjects;
            })
            .map(zEmployeeProjectsRepository::save);
    }

    /**
     * Get all the zEmployeeProjects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ZEmployeeProjects> findAll(Pageable pageable) {
        log.debug("Request to get all ZEmployeeProjects");
        return zEmployeeProjectsRepository.findAll(pageable);
    }

    /**
     * Get one zEmployeeProjects by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ZEmployeeProjects> findOne(Long id) {
        log.debug("Request to get ZEmployeeProjects : {}", id);
        return zEmployeeProjectsRepository.findById(id);
    }

    /**
     * Delete the zEmployeeProjects by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ZEmployeeProjects : {}", id);
        zEmployeeProjectsRepository.deleteById(id);
    }
}
