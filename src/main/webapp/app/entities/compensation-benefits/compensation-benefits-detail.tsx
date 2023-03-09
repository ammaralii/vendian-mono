import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './compensation-benefits.reducer';

export const CompensationBenefitsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const compensationBenefitsEntity = useAppSelector(state => state.compensationBenefits.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="compensationBenefitsDetailsHeading">Compensation Benefits</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{compensationBenefitsEntity.id}</dd>
          <dt>
            <span id="amount">Amount</span>
          </dt>
          <dd>{compensationBenefitsEntity.amount}</dd>
          <dt>
            <span id="isdeleted">Isdeleted</span>
          </dt>
          <dd>{compensationBenefitsEntity.isdeleted ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {compensationBenefitsEntity.createdat ? (
              <TextFormat value={compensationBenefitsEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {compensationBenefitsEntity.updatedat ? (
              <TextFormat value={compensationBenefitsEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Benefit</dt>
          <dd>{compensationBenefitsEntity.benefit ? compensationBenefitsEntity.benefit.name : ''}</dd>
          <dt>Employeecompensation</dt>
          <dd>{compensationBenefitsEntity.employeecompensation ? compensationBenefitsEntity.employeecompensation.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/compensation-benefits" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/compensation-benefits/${compensationBenefitsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CompensationBenefitsDetail;
