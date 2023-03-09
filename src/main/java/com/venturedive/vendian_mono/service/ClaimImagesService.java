package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.ClaimImages;
import com.venturedive.vendian_mono.repository.ClaimImagesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ClaimImages}.
 */
@Service
@Transactional
public class ClaimImagesService {

    private final Logger log = LoggerFactory.getLogger(ClaimImagesService.class);

    private final ClaimImagesRepository claimImagesRepository;

    public ClaimImagesService(ClaimImagesRepository claimImagesRepository) {
        this.claimImagesRepository = claimImagesRepository;
    }

    /**
     * Save a claimImages.
     *
     * @param claimImages the entity to save.
     * @return the persisted entity.
     */
    public ClaimImages save(ClaimImages claimImages) {
        log.debug("Request to save ClaimImages : {}", claimImages);
        return claimImagesRepository.save(claimImages);
    }

    /**
     * Update a claimImages.
     *
     * @param claimImages the entity to save.
     * @return the persisted entity.
     */
    public ClaimImages update(ClaimImages claimImages) {
        log.debug("Request to update ClaimImages : {}", claimImages);
        return claimImagesRepository.save(claimImages);
    }

    /**
     * Partially update a claimImages.
     *
     * @param claimImages the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClaimImages> partialUpdate(ClaimImages claimImages) {
        log.debug("Request to partially update ClaimImages : {}", claimImages);

        return claimImagesRepository
            .findById(claimImages.getId())
            .map(existingClaimImages -> {
                if (claimImages.getImages() != null) {
                    existingClaimImages.setImages(claimImages.getImages());
                }
                if (claimImages.getCreatedat() != null) {
                    existingClaimImages.setCreatedat(claimImages.getCreatedat());
                }
                if (claimImages.getUpdatedat() != null) {
                    existingClaimImages.setUpdatedat(claimImages.getUpdatedat());
                }

                return existingClaimImages;
            })
            .map(claimImagesRepository::save);
    }

    /**
     * Get all the claimImages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClaimImages> findAll(Pageable pageable) {
        log.debug("Request to get all ClaimImages");
        return claimImagesRepository.findAll(pageable);
    }

    /**
     * Get one claimImages by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClaimImages> findOne(Long id) {
        log.debug("Request to get ClaimImages : {}", id);
        return claimImagesRepository.findById(id);
    }

    /**
     * Delete the claimImages by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClaimImages : {}", id);
        claimImagesRepository.deleteById(id);
    }
}
