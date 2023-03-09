package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.ZLeaveRequests;
import com.venturedive.vendian_mono.repository.ZLeaveRequestsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ZLeaveRequests}.
 */
@Service
@Transactional
public class ZLeaveRequestsService {

    private final Logger log = LoggerFactory.getLogger(ZLeaveRequestsService.class);

    private final ZLeaveRequestsRepository zLeaveRequestsRepository;

    public ZLeaveRequestsService(ZLeaveRequestsRepository zLeaveRequestsRepository) {
        this.zLeaveRequestsRepository = zLeaveRequestsRepository;
    }

    /**
     * Save a zLeaveRequests.
     *
     * @param zLeaveRequests the entity to save.
     * @return the persisted entity.
     */
    public ZLeaveRequests save(ZLeaveRequests zLeaveRequests) {
        log.debug("Request to save ZLeaveRequests : {}", zLeaveRequests);
        return zLeaveRequestsRepository.save(zLeaveRequests);
    }

    /**
     * Update a zLeaveRequests.
     *
     * @param zLeaveRequests the entity to save.
     * @return the persisted entity.
     */
    public ZLeaveRequests update(ZLeaveRequests zLeaveRequests) {
        log.debug("Request to update ZLeaveRequests : {}", zLeaveRequests);
        return zLeaveRequestsRepository.save(zLeaveRequests);
    }

    /**
     * Partially update a zLeaveRequests.
     *
     * @param zLeaveRequests the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ZLeaveRequests> partialUpdate(ZLeaveRequests zLeaveRequests) {
        log.debug("Request to partially update ZLeaveRequests : {}", zLeaveRequests);

        return zLeaveRequestsRepository
            .findById(zLeaveRequests.getId())
            .map(existingZLeaveRequests -> {
                if (zLeaveRequests.getAction() != null) {
                    existingZLeaveRequests.setAction(zLeaveRequests.getAction());
                }
                if (zLeaveRequests.getUserid() != null) {
                    existingZLeaveRequests.setUserid(zLeaveRequests.getUserid());
                }
                if (zLeaveRequests.getManagerid() != null) {
                    existingZLeaveRequests.setManagerid(zLeaveRequests.getManagerid());
                }
                if (zLeaveRequests.getRequestparams() != null) {
                    existingZLeaveRequests.setRequestparams(zLeaveRequests.getRequestparams());
                }
                if (zLeaveRequests.getResponseparams() != null) {
                    existingZLeaveRequests.setResponseparams(zLeaveRequests.getResponseparams());
                }
                if (zLeaveRequests.getCreatedat() != null) {
                    existingZLeaveRequests.setCreatedat(zLeaveRequests.getCreatedat());
                }
                if (zLeaveRequests.getUpdatedat() != null) {
                    existingZLeaveRequests.setUpdatedat(zLeaveRequests.getUpdatedat());
                }

                return existingZLeaveRequests;
            })
            .map(zLeaveRequestsRepository::save);
    }

    /**
     * Get all the zLeaveRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ZLeaveRequests> findAll(Pageable pageable) {
        log.debug("Request to get all ZLeaveRequests");
        return zLeaveRequestsRepository.findAll(pageable);
    }

    /**
     * Get one zLeaveRequests by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ZLeaveRequests> findOne(Long id) {
        log.debug("Request to get ZLeaveRequests : {}", id);
        return zLeaveRequestsRepository.findById(id);
    }

    /**
     * Delete the zLeaveRequests by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ZLeaveRequests : {}", id);
        zLeaveRequestsRepository.deleteById(id);
    }
}
