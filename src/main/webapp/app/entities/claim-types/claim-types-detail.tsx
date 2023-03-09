import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './claim-types.reducer';

export const ClaimTypesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const claimTypesEntity = useAppSelector(state => state.claimTypes.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="claimTypesDetailsHeading">Claim Types</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{claimTypesEntity.id}</dd>
          <dt>
            <span id="claimtype">Claimtype</span>
          </dt>
          <dd>{claimTypesEntity.claimtype}</dd>
          <dt>
            <span id="daterangerequired">Daterangerequired</span>
          </dt>
          <dd>{claimTypesEntity.daterangerequired ? 'true' : 'false'}</dd>
          <dt>
            <span id="descriptionrequired">Descriptionrequired</span>
          </dt>
          <dd>{claimTypesEntity.descriptionrequired ? 'true' : 'false'}</dd>
          <dt>
            <span id="parentid">Parentid</span>
          </dt>
          <dd>{claimTypesEntity.parentid}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {claimTypesEntity.createdat ? <TextFormat value={claimTypesEntity.createdat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {claimTypesEntity.updatedat ? <TextFormat value={claimTypesEntity.updatedat} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/claim-types" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/claim-types/${claimTypesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClaimTypesDetail;
