import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './approvers.reducer';

export const ApproversDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const approversEntity = useAppSelector(state => state.approvers.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="approversDetailsHeading">Approvers</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{approversEntity.id}</dd>
          <dt>
            <span id="userId">User Id</span>
          </dt>
          <dd>{approversEntity.userId}</dd>
          <dt>
            <span id="reference">Reference</span>
          </dt>
          <dd>{approversEntity.reference}</dd>
          <dt>
            <span id="as">As</span>
          </dt>
          <dd>{approversEntity.as}</dd>
          <dt>
            <span id="comment">Comment</span>
          </dt>
          <dd>{approversEntity.comment}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{approversEntity.status}</dd>
          <dt>
            <span id="stausDate">Staus Date</span>
          </dt>
          <dd>
            {approversEntity.stausDate ? <TextFormat value={approversEntity.stausDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="priority">Priority</span>
          </dt>
          <dd>{approversEntity.priority}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {approversEntity.createdAt ? <TextFormat value={approversEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {approversEntity.updatedAt ? <TextFormat value={approversEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="deletedAt">Deleted At</span>
          </dt>
          <dd>
            {approversEntity.deletedAt ? <TextFormat value={approversEntity.deletedAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{approversEntity.version}</dd>
          <dt>Approver Group</dt>
          <dd>{approversEntity.approverGroup ? approversEntity.approverGroup.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/approvers" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/approvers/${approversEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ApproversDetail;
