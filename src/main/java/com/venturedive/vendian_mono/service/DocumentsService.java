package com.venturedive.vendian_mono.service;

import com.venturedive.vendian_mono.domain.Documents;
import com.venturedive.vendian_mono.repository.DocumentsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Documents}.
 */
@Service
@Transactional
public class DocumentsService {

    private final Logger log = LoggerFactory.getLogger(DocumentsService.class);

    private final DocumentsRepository documentsRepository;

    public DocumentsService(DocumentsRepository documentsRepository) {
        this.documentsRepository = documentsRepository;
    }

    /**
     * Save a documents.
     *
     * @param documents the entity to save.
     * @return the persisted entity.
     */
    public Documents save(Documents documents) {
        log.debug("Request to save Documents : {}", documents);
        return documentsRepository.save(documents);
    }

    /**
     * Update a documents.
     *
     * @param documents the entity to save.
     * @return the persisted entity.
     */
    public Documents update(Documents documents) {
        log.debug("Request to update Documents : {}", documents);
        return documentsRepository.save(documents);
    }

    /**
     * Partially update a documents.
     *
     * @param documents the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Documents> partialUpdate(Documents documents) {
        log.debug("Request to partially update Documents : {}", documents);

        return documentsRepository
            .findById(documents.getId())
            .map(existingDocuments -> {
                if (documents.getName() != null) {
                    existingDocuments.setName(documents.getName());
                }
                if (documents.getNameContentType() != null) {
                    existingDocuments.setNameContentType(documents.getNameContentType());
                }
                if (documents.getUrl() != null) {
                    existingDocuments.setUrl(documents.getUrl());
                }
                if (documents.getUrlContentType() != null) {
                    existingDocuments.setUrlContentType(documents.getUrlContentType());
                }
                if (documents.getCreatedat() != null) {
                    existingDocuments.setCreatedat(documents.getCreatedat());
                }
                if (documents.getUpdatedat() != null) {
                    existingDocuments.setUpdatedat(documents.getUpdatedat());
                }

                return existingDocuments;
            })
            .map(documentsRepository::save);
    }

    /**
     * Get all the documents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Documents> findAll(Pageable pageable) {
        log.debug("Request to get all Documents");
        return documentsRepository.findAll(pageable);
    }

    /**
     * Get one documents by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Documents> findOne(Long id) {
        log.debug("Request to get Documents : {}", id);
        return documentsRepository.findById(id);
    }

    /**
     * Delete the documents by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Documents : {}", id);
        documentsRepository.deleteById(id);
    }
}
