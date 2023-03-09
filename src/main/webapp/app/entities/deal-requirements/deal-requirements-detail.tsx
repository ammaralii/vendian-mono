import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './deal-requirements.reducer';

export const DealRequirementsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const dealRequirementsEntity = useAppSelector(state => state.dealRequirements.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="dealRequirementsDetailsHeading">Deal Requirements</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{dealRequirementsEntity.id}</dd>
          <dt>
            <span id="dealreqidentifier">Dealreqidentifier</span>
          </dt>
          <dd>{dealRequirementsEntity.dealreqidentifier}</dd>
          <dt>
            <span id="competencyname">Competencyname</span>
          </dt>
          <dd>{dealRequirementsEntity.competencyname}</dd>
          <dt>
            <span id="skills">Skills</span>
          </dt>
          <dd>{dealRequirementsEntity.skills}</dd>
          <dt>
            <span id="resourcerequired">Resourcerequired</span>
          </dt>
          <dd>{dealRequirementsEntity.resourcerequired}</dd>
          <dt>
            <span id="minexperiencelevel">Minexperiencelevel</span>
          </dt>
          <dd>{dealRequirementsEntity.minexperiencelevel}</dd>
          <dt>
            <span id="maxexperiencelevel">Maxexperiencelevel</span>
          </dt>
          <dd>{dealRequirementsEntity.maxexperiencelevel}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {dealRequirementsEntity.createdat ? (
              <TextFormat value={dealRequirementsEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {dealRequirementsEntity.updatedat ? (
              <TextFormat value={dealRequirementsEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="deletedat">Deletedat</span>
          </dt>
          <dd>
            {dealRequirementsEntity.deletedat ? (
              <TextFormat value={dealRequirementsEntity.deletedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Deal</dt>
          <dd>{dealRequirementsEntity.deal ? dealRequirementsEntity.deal.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/deal-requirements" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/deal-requirements/${dealRequirementsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DealRequirementsDetail;
