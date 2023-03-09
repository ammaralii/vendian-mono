import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './documents.reducer';

export const DocumentsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const documentsEntity = useAppSelector(state => state.documents.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="documentsDetailsHeading">Documents</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{documentsEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>
            {documentsEntity.name ? (
              <div>
                {documentsEntity.nameContentType ? (
                  <a onClick={openFile(documentsEntity.nameContentType, documentsEntity.name)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {documentsEntity.nameContentType}, {byteSize(documentsEntity.name)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="url">Url</span>
          </dt>
          <dd>
            {documentsEntity.url ? (
              <div>
                {documentsEntity.urlContentType ? (
                  <a onClick={openFile(documentsEntity.urlContentType, documentsEntity.url)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {documentsEntity.urlContentType}, {byteSize(documentsEntity.url)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {documentsEntity.createdat ? <TextFormat value={documentsEntity.createdat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {documentsEntity.updatedat ? <TextFormat value={documentsEntity.updatedat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/documents" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/documents/${documentsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DocumentsDetail;
