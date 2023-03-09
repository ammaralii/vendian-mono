package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.Deals;
import com.venturedive.vendian_mono.repository.DealsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Deals}.
 */
@Service
@Transactional
public class DealsService {

    private final Logger log = LoggerFactory.getLogger(DealsService.class);

    private final DealsRepository dealsRepository;

    public DealsService(DealsRepository dealsRepository) {
        this.dealsRepository = dealsRepository;
    }

    /**
     * Save a deals.
     *
     * @param deals the entity to save.
     * @return the persisted entity.
     */
    public Deals save(Deals deals) {
        log.debug("Request to save Deals : {}", deals);
        return dealsRepository.save(deals);
    }

    /**
     * Update a deals.
     *
     * @param deals the entity to save.
     * @return the persisted entity.
     */
    public Deals update(Deals deals) {
        log.debug("Request to update Deals : {}", deals);
        return dealsRepository.save(deals);
    }

    /**
     * Partially update a deals.
     *
     * @param deals the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Deals> partialUpdate(Deals deals) {
        log.debug("Request to partially update Deals : {}", deals);

        return dealsRepository
            .findById(deals.getId())
            .map(existingDeals -> {
                if (deals.getDealnumber() != null) {
                    existingDeals.setDealnumber(deals.getDealnumber());
                }
                if (deals.getDealname() != null) {
                    existingDeals.setDealname(deals.getDealname());
                }
                if (deals.getBusinessunit() != null) {
                    existingDeals.setBusinessunit(deals.getBusinessunit());
                }
                if (deals.getClientname() != null) {
                    existingDeals.setClientname(deals.getClientname());
                }
                if (deals.getDealowner() != null) {
                    existingDeals.setDealowner(deals.getDealowner());
                }
                if (deals.getProposaltype() != null) {
                    existingDeals.setProposaltype(deals.getProposaltype());
                }
                if (deals.getProjectid() != null) {
                    existingDeals.setProjectid(deals.getProjectid());
                }
                if (deals.getExpectedstartdate() != null) {
                    existingDeals.setExpectedstartdate(deals.getExpectedstartdate());
                }
                if (deals.getStage() != null) {
                    existingDeals.setStage(deals.getStage());
                }
                if (deals.getProbability() != null) {
                    existingDeals.setProbability(deals.getProbability());
                }
                if (deals.getProjectduration() != null) {
                    existingDeals.setProjectduration(deals.getProjectduration());
                }
                if (deals.getType() != null) {
                    existingDeals.setType(deals.getType());
                }
                if (deals.getStatus() != null) {
                    existingDeals.setStatus(deals.getStatus());
                }
                if (deals.getClosedat() != null) {
                    existingDeals.setClosedat(deals.getClosedat());
                }
                if (deals.getCreatedat() != null) {
                    existingDeals.setCreatedat(deals.getCreatedat());
                }
                if (deals.getUpdatedat() != null) {
                    existingDeals.setUpdatedat(deals.getUpdatedat());
                }
                if (deals.getDeletedat() != null) {
                    existingDeals.setDeletedat(deals.getDeletedat());
                }
                if (deals.getResourcingenteredinvendians() != null) {
                    existingDeals.setResourcingenteredinvendians(deals.getResourcingenteredinvendians());
                }

                return existingDeals;
            })
            .map(dealsRepository::save);
    }

    /**
     * Get all the deals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Deals> findAll(Pageable pageable) {
        log.debug("Request to get all Deals");
        return dealsRepository.findAll(pageable);
    }

    /**
     * Get one deals by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Deals> findOne(Long id) {
        log.debug("Request to get Deals : {}", id);
        return dealsRepository.findById(id);
    }

    /**
     * Delete the deals by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Deals : {}", id);
        dealsRepository.deleteById(id);
    }
}
