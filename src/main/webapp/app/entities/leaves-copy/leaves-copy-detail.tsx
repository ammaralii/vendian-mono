import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './leaves-copy.reducer';

export const LeavesCopyDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leavesCopyEntity = useAppSelector(state => state.leavesCopy.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leavesCopyDetailsHeading">Leaves Copy</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{leavesCopyEntity.id}</dd>
          <dt>
            <span id="annualtotal">Annualtotal</span>
          </dt>
          <dd>{leavesCopyEntity.annualtotal}</dd>
          <dt>
            <span id="annualused">Annualused</span>
          </dt>
          <dd>{leavesCopyEntity.annualused}</dd>
          <dt>
            <span id="annualadjustments">Annualadjustments</span>
          </dt>
          <dd>{leavesCopyEntity.annualadjustments}</dd>
          <dt>
            <span id="casualtotal">Casualtotal</span>
          </dt>
          <dd>{leavesCopyEntity.casualtotal}</dd>
          <dt>
            <span id="casualused">Casualused</span>
          </dt>
          <dd>{leavesCopyEntity.casualused}</dd>
          <dt>
            <span id="sicktotal">Sicktotal</span>
          </dt>
          <dd>{leavesCopyEntity.sicktotal}</dd>
          <dt>
            <span id="sickused">Sickused</span>
          </dt>
          <dd>{leavesCopyEntity.sickused}</dd>
          <dt>
            <span id="annualtotalnextyear">Annualtotalnextyear</span>
          </dt>
          <dd>{leavesCopyEntity.annualtotalnextyear}</dd>
          <dt>
            <span id="annualusednextyear">Annualusednextyear</span>
          </dt>
          <dd>{leavesCopyEntity.annualusednextyear}</dd>
          <dt>
            <span id="casualtotalnextyear">Casualtotalnextyear</span>
          </dt>
          <dd>{leavesCopyEntity.casualtotalnextyear}</dd>
          <dt>
            <span id="casualusednextyear">Casualusednextyear</span>
          </dt>
          <dd>{leavesCopyEntity.casualusednextyear}</dd>
          <dt>
            <span id="sicktotalnextyear">Sicktotalnextyear</span>
          </dt>
          <dd>{leavesCopyEntity.sicktotalnextyear}</dd>
          <dt>
            <span id="sickusednextyear">Sickusednextyear</span>
          </dt>
          <dd>{leavesCopyEntity.sickusednextyear}</dd>
          <dt>
            <span id="carryforward">Carryforward</span>
          </dt>
          <dd>{leavesCopyEntity.carryforward}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {leavesCopyEntity.createdat ? <TextFormat value={leavesCopyEntity.createdat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {leavesCopyEntity.updatedat ? <TextFormat value={leavesCopyEntity.updatedat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/leaves-copy" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/leaves-copy/${leavesCopyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeavesCopyDetail;
