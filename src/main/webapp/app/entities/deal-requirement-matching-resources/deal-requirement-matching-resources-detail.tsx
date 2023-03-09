import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './deal-requirement-matching-resources.reducer';

export const DealRequirementMatchingResourcesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const dealRequirementMatchingResourcesEntity = useAppSelector(state => state.dealRequirementMatchingResources.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="dealRequirementMatchingResourcesDetailsHeading">Deal Requirement Matching Resources</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{dealRequirementMatchingResourcesEntity.id}</dd>
          <dt>
            <span id="comments">Comments</span>
          </dt>
          <dd>{dealRequirementMatchingResourcesEntity.comments}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {dealRequirementMatchingResourcesEntity.createdat ? (
              <TextFormat value={dealRequirementMatchingResourcesEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {dealRequirementMatchingResourcesEntity.updatedat ? (
              <TextFormat value={dealRequirementMatchingResourcesEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="deletedat">Deletedat</span>
          </dt>
          <dd>
            {dealRequirementMatchingResourcesEntity.deletedat ? (
              <TextFormat value={dealRequirementMatchingResourcesEntity.deletedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Dealrequirement</dt>
          <dd>{dealRequirementMatchingResourcesEntity.dealrequirement ? dealRequirementMatchingResourcesEntity.dealrequirement.id : ''}</dd>
          <dt>Resource</dt>
          <dd>{dealRequirementMatchingResourcesEntity.resource ? dealRequirementMatchingResourcesEntity.resource.id : ''}</dd>
          <dt>Resourcestatus</dt>
          <dd>
            {dealRequirementMatchingResourcesEntity.resourcestatus ? dealRequirementMatchingResourcesEntity.resourcestatus.systemKey : ''}
          </dd>
          <dt>Systemstatus</dt>
          <dd>
            {dealRequirementMatchingResourcesEntity.systemstatus ? dealRequirementMatchingResourcesEntity.systemstatus.systemKey : ''}
          </dd>
        </dl>
        <Button tag={Link} to="/deal-requirement-matching-resources" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/deal-requirement-matching-resources/${dealRequirementMatchingResourcesEntity.id}/edit`}
          replace
          color="primary"
        >
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DealRequirementMatchingResourcesDetail;
