package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.UserRatingsRemarks;
import com.venturedive.vendian_mono.repository.UserRatingsRemarksRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserRatingsRemarks}.
 */
@Service
@Transactional
public class UserRatingsRemarksService {

    private final Logger log = LoggerFactory.getLogger(UserRatingsRemarksService.class);

    private final UserRatingsRemarksRepository userRatingsRemarksRepository;

    public UserRatingsRemarksService(UserRatingsRemarksRepository userRatingsRemarksRepository) {
        this.userRatingsRemarksRepository = userRatingsRemarksRepository;
    }

    /**
     * Save a userRatingsRemarks.
     *
     * @param userRatingsRemarks the entity to save.
     * @return the persisted entity.
     */
    public UserRatingsRemarks save(UserRatingsRemarks userRatingsRemarks) {
        log.debug("Request to save UserRatingsRemarks : {}", userRatingsRemarks);
        return userRatingsRemarksRepository.save(userRatingsRemarks);
    }

    /**
     * Update a userRatingsRemarks.
     *
     * @param userRatingsRemarks the entity to save.
     * @return the persisted entity.
     */
    public UserRatingsRemarks update(UserRatingsRemarks userRatingsRemarks) {
        log.debug("Request to update UserRatingsRemarks : {}", userRatingsRemarks);
        return userRatingsRemarksRepository.save(userRatingsRemarks);
    }

    /**
     * Partially update a userRatingsRemarks.
     *
     * @param userRatingsRemarks the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserRatingsRemarks> partialUpdate(UserRatingsRemarks userRatingsRemarks) {
        log.debug("Request to partially update UserRatingsRemarks : {}", userRatingsRemarks);

        return userRatingsRemarksRepository
            .findById(userRatingsRemarks.getId())
            .map(existingUserRatingsRemarks -> {
                if (userRatingsRemarks.getIsPromotion() != null) {
                    existingUserRatingsRemarks.setIsPromotion(userRatingsRemarks.getIsPromotion());
                }
                if (userRatingsRemarks.getStrength() != null) {
                    existingUserRatingsRemarks.setStrength(userRatingsRemarks.getStrength());
                }
                if (userRatingsRemarks.getAreaOfImprovement() != null) {
                    existingUserRatingsRemarks.setAreaOfImprovement(userRatingsRemarks.getAreaOfImprovement());
                }
                if (userRatingsRemarks.getPromotionJustification() != null) {
                    existingUserRatingsRemarks.setPromotionJustification(userRatingsRemarks.getPromotionJustification());
                }
                if (userRatingsRemarks.getNewGrade() != null) {
                    existingUserRatingsRemarks.setNewGrade(userRatingsRemarks.getNewGrade());
                }
                if (userRatingsRemarks.getIsRedesignation() != null) {
                    existingUserRatingsRemarks.setIsRedesignation(userRatingsRemarks.getIsRedesignation());
                }
                if (userRatingsRemarks.getRecommendedSalary() != null) {
                    existingUserRatingsRemarks.setRecommendedSalary(userRatingsRemarks.getRecommendedSalary());
                }
                if (userRatingsRemarks.getStatus() != null) {
                    existingUserRatingsRemarks.setStatus(userRatingsRemarks.getStatus());
                }
                if (userRatingsRemarks.getCreatedAt() != null) {
                    existingUserRatingsRemarks.setCreatedAt(userRatingsRemarks.getCreatedAt());
                }
                if (userRatingsRemarks.getUpdatedAt() != null) {
                    existingUserRatingsRemarks.setUpdatedAt(userRatingsRemarks.getUpdatedAt());
                }
                if (userRatingsRemarks.getDeletedAt() != null) {
                    existingUserRatingsRemarks.setDeletedAt(userRatingsRemarks.getDeletedAt());
                }
                if (userRatingsRemarks.getVersion() != null) {
                    existingUserRatingsRemarks.setVersion(userRatingsRemarks.getVersion());
                }
                if (userRatingsRemarks.getOtherComments() != null) {
                    existingUserRatingsRemarks.setOtherComments(userRatingsRemarks.getOtherComments());
                }

                return existingUserRatingsRemarks;
            })
            .map(userRatingsRemarksRepository::save);
    }

    /**
     * Get all the userRatingsRemarks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserRatingsRemarks> findAll(Pageable pageable) {
        log.debug("Request to get all UserRatingsRemarks");
        return userRatingsRemarksRepository.findAll(pageable);
    }

    /**
     * Get one userRatingsRemarks by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserRatingsRemarks> findOne(Long id) {
        log.debug("Request to get UserRatingsRemarks : {}", id);
        return userRatingsRemarksRepository.findById(id);
    }

    /**
     * Delete the userRatingsRemarks by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserRatingsRemarks : {}", id);
        userRatingsRemarksRepository.deleteById(id);
    }
}
