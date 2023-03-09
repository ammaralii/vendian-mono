import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './deal-resources.reducer';

export const DealResourcesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const dealResourcesEntity = useAppSelector(state => state.dealResources.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="dealResourcesDetailsHeading">Deal Resources</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{dealResourcesEntity.id}</dd>
          <dt>
            <span id="firstname">Firstname</span>
          </dt>
          <dd>{dealResourcesEntity.firstname}</dd>
          <dt>
            <span id="lastname">Lastname</span>
          </dt>
          <dd>{dealResourcesEntity.lastname}</dd>
          <dt>
            <span id="joiningdate">Joiningdate</span>
          </dt>
          <dd>
            {dealResourcesEntity.joiningdate ? (
              <TextFormat value={dealResourcesEntity.joiningdate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="externalexpyears">Externalexpyears</span>
          </dt>
          <dd>{dealResourcesEntity.externalexpyears}</dd>
          <dt>
            <span id="externalexpmonths">Externalexpmonths</span>
          </dt>
          <dd>{dealResourcesEntity.externalexpmonths}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {dealResourcesEntity.createdat ? (
              <TextFormat value={dealResourcesEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {dealResourcesEntity.updatedat ? (
              <TextFormat value={dealResourcesEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{dealResourcesEntity.type}</dd>
          <dt>
            <span id="isactive">Isactive</span>
          </dt>
          <dd>{dealResourcesEntity.isactive ? 'true' : 'false'}</dd>
          <dt>Employee</dt>
          <dd>{dealResourcesEntity.employee ? dealResourcesEntity.employee.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/deal-resources" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/deal-resources/${dealResourcesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DealResourcesDetail;
