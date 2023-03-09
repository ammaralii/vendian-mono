package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.Skills;
import com.venturedive.vendian_mono.repository.SkillsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Skills}.
 */
@Service
@Transactional
public class SkillsService {

    private final Logger log = LoggerFactory.getLogger(SkillsService.class);

    private final SkillsRepository skillsRepository;

    public SkillsService(SkillsRepository skillsRepository) {
        this.skillsRepository = skillsRepository;
    }

    /**
     * Save a skills.
     *
     * @param skills the entity to save.
     * @return the persisted entity.
     */
    public Skills save(Skills skills) {
        log.debug("Request to save Skills : {}", skills);
        return skillsRepository.save(skills);
    }

    /**
     * Update a skills.
     *
     * @param skills the entity to save.
     * @return the persisted entity.
     */
    public Skills update(Skills skills) {
        log.debug("Request to update Skills : {}", skills);
        return skillsRepository.save(skills);
    }

    /**
     * Partially update a skills.
     *
     * @param skills the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Skills> partialUpdate(Skills skills) {
        log.debug("Request to partially update Skills : {}", skills);

        return skillsRepository
            .findById(skills.getId())
            .map(existingSkills -> {
                if (skills.getName() != null) {
                    existingSkills.setName(skills.getName());
                }
                if (skills.getCreatedat() != null) {
                    existingSkills.setCreatedat(skills.getCreatedat());
                }
                if (skills.getUpdatedat() != null) {
                    existingSkills.setUpdatedat(skills.getUpdatedat());
                }

                return existingSkills;
            })
            .map(skillsRepository::save);
    }

    /**
     * Get all the skills.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Skills> findAll(Pageable pageable) {
        log.debug("Request to get all Skills");
        return skillsRepository.findAll(pageable);
    }

    /**
     * Get one skills by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Skills> findOne(Long id) {
        log.debug("Request to get Skills : {}", id);
        return skillsRepository.findById(id);
    }

    /**
     * Delete the skills by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Skills : {}", id);
        skillsRepository.deleteById(id);
    }
}
