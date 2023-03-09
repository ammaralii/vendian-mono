package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.Tracks;
import com.venturedive.vendian_mono.repository.TracksRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Tracks}.
 */
@Service
@Transactional
public class TracksService {

    private final Logger log = LoggerFactory.getLogger(TracksService.class);

    private final TracksRepository tracksRepository;

    public TracksService(TracksRepository tracksRepository) {
        this.tracksRepository = tracksRepository;
    }

    /**
     * Save a tracks.
     *
     * @param tracks the entity to save.
     * @return the persisted entity.
     */
    public Tracks save(Tracks tracks) {
        log.debug("Request to save Tracks : {}", tracks);
        return tracksRepository.save(tracks);
    }

    /**
     * Update a tracks.
     *
     * @param tracks the entity to save.
     * @return the persisted entity.
     */
    public Tracks update(Tracks tracks) {
        log.debug("Request to update Tracks : {}", tracks);
        return tracksRepository.save(tracks);
    }

    /**
     * Partially update a tracks.
     *
     * @param tracks the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Tracks> partialUpdate(Tracks tracks) {
        log.debug("Request to partially update Tracks : {}", tracks);

        return tracksRepository
            .findById(tracks.getId())
            .map(existingTracks -> {
                if (tracks.getName() != null) {
                    existingTracks.setName(tracks.getName());
                }
                if (tracks.getDescription() != null) {
                    existingTracks.setDescription(tracks.getDescription());
                }
                if (tracks.getCreatedat() != null) {
                    existingTracks.setCreatedat(tracks.getCreatedat());
                }
                if (tracks.getUpdatedat() != null) {
                    existingTracks.setUpdatedat(tracks.getUpdatedat());
                }
                if (tracks.getDeletedat() != null) {
                    existingTracks.setDeletedat(tracks.getDeletedat());
                }

                return existingTracks;
            })
            .map(tracksRepository::save);
    }

    /**
     * Get all the tracks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Tracks> findAll(Pageable pageable) {
        log.debug("Request to get all Tracks");
        return tracksRepository.findAll(pageable);
    }

    /**
     * Get one tracks by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Tracks> findOne(Long id) {
        log.debug("Request to get Tracks : {}", id);
        return tracksRepository.findById(id);
    }

    /**
     * Delete the tracks by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Tracks : {}", id);
        tracksRepository.deleteById(id);
    }
}
