import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './deals.reducer';

export const DealsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const dealsEntity = useAppSelector(state => state.deals.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="dealsDetailsHeading">Deals</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{dealsEntity.id}</dd>
          <dt>
            <span id="dealnumber">Dealnumber</span>
          </dt>
          <dd>{dealsEntity.dealnumber}</dd>
          <dt>
            <span id="dealname">Dealname</span>
          </dt>
          <dd>{dealsEntity.dealname}</dd>
          <dt>
            <span id="businessunit">Businessunit</span>
          </dt>
          <dd>{dealsEntity.businessunit}</dd>
          <dt>
            <span id="clientname">Clientname</span>
          </dt>
          <dd>{dealsEntity.clientname}</dd>
          <dt>
            <span id="dealowner">Dealowner</span>
          </dt>
          <dd>{dealsEntity.dealowner}</dd>
          <dt>
            <span id="proposaltype">Proposaltype</span>
          </dt>
          <dd>{dealsEntity.proposaltype}</dd>
          <dt>
            <span id="projectid">Projectid</span>
          </dt>
          <dd>{dealsEntity.projectid}</dd>
          <dt>
            <span id="expectedstartdate">Expectedstartdate</span>
          </dt>
          <dd>
            {dealsEntity.expectedstartdate ? (
              <TextFormat value={dealsEntity.expectedstartdate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="stage">Stage</span>
          </dt>
          <dd>{dealsEntity.stage}</dd>
          <dt>
            <span id="probability">Probability</span>
          </dt>
          <dd>{dealsEntity.probability}</dd>
          <dt>
            <span id="projectduration">Projectduration</span>
          </dt>
          <dd>{dealsEntity.projectduration}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{dealsEntity.type}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{dealsEntity.status}</dd>
          <dt>
            <span id="closedat">Closedat</span>
          </dt>
          <dd>{dealsEntity.closedat ? <TextFormat value={dealsEntity.closedat} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>{dealsEntity.createdat ? <TextFormat value={dealsEntity.createdat} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>{dealsEntity.updatedat ? <TextFormat value={dealsEntity.updatedat} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="deletedat">Deletedat</span>
          </dt>
          <dd>{dealsEntity.deletedat ? <TextFormat value={dealsEntity.deletedat} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="resourcingenteredinvendians">Resourcingenteredinvendians</span>
          </dt>
          <dd>{dealsEntity.resourcingenteredinvendians ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/deals" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/deals/${dealsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DealsDetail;
