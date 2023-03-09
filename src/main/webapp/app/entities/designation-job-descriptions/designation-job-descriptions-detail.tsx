import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './designation-job-descriptions.reducer';

export const DesignationJobDescriptionsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const designationJobDescriptionsEntity = useAppSelector(state => state.designationJobDescriptions.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="designationJobDescriptionsDetailsHeading">Designation Job Descriptions</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{designationJobDescriptionsEntity.id}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {designationJobDescriptionsEntity.createdat ? (
              <TextFormat value={designationJobDescriptionsEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {designationJobDescriptionsEntity.updatedat ? (
              <TextFormat value={designationJobDescriptionsEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Document</dt>
          <dd>{designationJobDescriptionsEntity.document ? designationJobDescriptionsEntity.document.id : ''}</dd>
          <dt>Designation</dt>
          <dd>{designationJobDescriptionsEntity.designation ? designationJobDescriptionsEntity.designation.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/designation-job-descriptions" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/designation-job-descriptions/${designationJobDescriptionsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DesignationJobDescriptionsDetail;
