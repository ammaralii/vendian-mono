import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './leaves-olds.reducer';

export const LeavesOldsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leavesOldsEntity = useAppSelector(state => state.leavesOlds.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leavesOldsDetailsHeading">Leaves Olds</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{leavesOldsEntity.id}</dd>
          <dt>
            <span id="annualtotal">Annualtotal</span>
          </dt>
          <dd>{leavesOldsEntity.annualtotal}</dd>
          <dt>
            <span id="annualused">Annualused</span>
          </dt>
          <dd>{leavesOldsEntity.annualused}</dd>
          <dt>
            <span id="annualadjustments">Annualadjustments</span>
          </dt>
          <dd>{leavesOldsEntity.annualadjustments}</dd>
          <dt>
            <span id="casualtotal">Casualtotal</span>
          </dt>
          <dd>{leavesOldsEntity.casualtotal}</dd>
          <dt>
            <span id="casualused">Casualused</span>
          </dt>
          <dd>{leavesOldsEntity.casualused}</dd>
          <dt>
            <span id="sicktotal">Sicktotal</span>
          </dt>
          <dd>{leavesOldsEntity.sicktotal}</dd>
          <dt>
            <span id="sickused">Sickused</span>
          </dt>
          <dd>{leavesOldsEntity.sickused}</dd>
          <dt>
            <span id="annualtotalnextyear">Annualtotalnextyear</span>
          </dt>
          <dd>{leavesOldsEntity.annualtotalnextyear}</dd>
          <dt>
            <span id="annualusednextyear">Annualusednextyear</span>
          </dt>
          <dd>{leavesOldsEntity.annualusednextyear}</dd>
          <dt>
            <span id="casualtotalnextyear">Casualtotalnextyear</span>
          </dt>
          <dd>{leavesOldsEntity.casualtotalnextyear}</dd>
          <dt>
            <span id="casualusednextyear">Casualusednextyear</span>
          </dt>
          <dd>{leavesOldsEntity.casualusednextyear}</dd>
          <dt>
            <span id="sicktotalnextyear">Sicktotalnextyear</span>
          </dt>
          <dd>{leavesOldsEntity.sicktotalnextyear}</dd>
          <dt>
            <span id="sickusednextyear">Sickusednextyear</span>
          </dt>
          <dd>{leavesOldsEntity.sickusednextyear}</dd>
          <dt>
            <span id="carryforward">Carryforward</span>
          </dt>
          <dd>{leavesOldsEntity.carryforward}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {leavesOldsEntity.createdat ? <TextFormat value={leavesOldsEntity.createdat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {leavesOldsEntity.updatedat ? <TextFormat value={leavesOldsEntity.updatedat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/leaves-olds" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/leaves-olds/${leavesOldsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeavesOldsDetail;
