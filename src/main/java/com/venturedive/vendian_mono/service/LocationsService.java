package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.Locations;
import com.venturedive.vendian_mono.repository.LocationsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Locations}.
 */
@Service
@Transactional
public class LocationsService {

    private final Logger log = LoggerFactory.getLogger(LocationsService.class);

    private final LocationsRepository locationsRepository;

    public LocationsService(LocationsRepository locationsRepository) {
        this.locationsRepository = locationsRepository;
    }

    /**
     * Save a locations.
     *
     * @param locations the entity to save.
     * @return the persisted entity.
     */
    public Locations save(Locations locations) {
        log.debug("Request to save Locations : {}", locations);
        return locationsRepository.save(locations);
    }

    /**
     * Update a locations.
     *
     * @param locations the entity to save.
     * @return the persisted entity.
     */
    public Locations update(Locations locations) {
        log.debug("Request to update Locations : {}", locations);
        return locationsRepository.save(locations);
    }

    /**
     * Partially update a locations.
     *
     * @param locations the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Locations> partialUpdate(Locations locations) {
        log.debug("Request to partially update Locations : {}", locations);

        return locationsRepository
            .findById(locations.getId())
            .map(existingLocations -> {
                if (locations.getName() != null) {
                    existingLocations.setName(locations.getName());
                }
                if (locations.getCreatedat() != null) {
                    existingLocations.setCreatedat(locations.getCreatedat());
                }
                if (locations.getUpdatedat() != null) {
                    existingLocations.setUpdatedat(locations.getUpdatedat());
                }
                if (locations.getDeletedat() != null) {
                    existingLocations.setDeletedat(locations.getDeletedat());
                }

                return existingLocations;
            })
            .map(locationsRepository::save);
    }

    /**
     * Get all the locations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Locations> findAll(Pageable pageable) {
        log.debug("Request to get all Locations");
        return locationsRepository.findAll(pageable);
    }

    /**
     * Get one locations by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Locations> findOne(Long id) {
        log.debug("Request to get Locations : {}", id);
        return locationsRepository.findById(id);
    }

    /**
     * Delete the locations by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Locations : {}", id);
        locationsRepository.deleteById(id);
    }
}
