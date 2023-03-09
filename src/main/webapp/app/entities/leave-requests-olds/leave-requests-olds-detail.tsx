import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './leave-requests-olds.reducer';

export const LeaveRequestsOldsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leaveRequestsOldsEntity = useAppSelector(state => state.leaveRequestsOlds.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leaveRequestsOldsDetailsHeading">Leave Requests Olds</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{leaveRequestsOldsEntity.id}</dd>
          <dt>
            <span id="startdate">Startdate</span>
          </dt>
          <dd>
            {leaveRequestsOldsEntity.startdate ? (
              <TextFormat value={leaveRequestsOldsEntity.startdate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="enddate">Enddate</span>
          </dt>
          <dd>
            {leaveRequestsOldsEntity.enddate ? (
              <TextFormat value={leaveRequestsOldsEntity.enddate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="requesternote">Requesternote</span>
          </dt>
          <dd>{leaveRequestsOldsEntity.requesternote}</dd>
          <dt>
            <span id="managernote">Managernote</span>
          </dt>
          <dd>{leaveRequestsOldsEntity.managernote}</dd>
          <dt>
            <span id="currentstatus">Currentstatus</span>
          </dt>
          <dd>{leaveRequestsOldsEntity.currentstatus}</dd>
          <dt>
            <span id="leavescanceled">Leavescanceled</span>
          </dt>
          <dd>{leaveRequestsOldsEntity.leavescanceled ? 'true' : 'false'}</dd>
          <dt>
            <span id="requestdate">Requestdate</span>
          </dt>
          <dd>
            {leaveRequestsOldsEntity.requestdate ? (
              <TextFormat value={leaveRequestsOldsEntity.requestdate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="linkstring">Linkstring</span>
          </dt>
          <dd>{leaveRequestsOldsEntity.linkstring}</dd>
          <dt>
            <span id="linkused">Linkused</span>
          </dt>
          <dd>{leaveRequestsOldsEntity.linkused ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {leaveRequestsOldsEntity.createdat ? (
              <TextFormat value={leaveRequestsOldsEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {leaveRequestsOldsEntity.updatedat ? (
              <TextFormat value={leaveRequestsOldsEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="ishalfday">Ishalfday</span>
          </dt>
          <dd>{leaveRequestsOldsEntity.ishalfday ? 'true' : 'false'}</dd>
          <dt>
            <span id="actiondate">Actiondate</span>
          </dt>
          <dd>
            {leaveRequestsOldsEntity.actiondate ? (
              <TextFormat value={leaveRequestsOldsEntity.actiondate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lmstatus">Lmstatus</span>
          </dt>
          <dd>{leaveRequestsOldsEntity.lmstatus}</dd>
          <dt>
            <span id="pmstatus">Pmstatus</span>
          </dt>
          <dd>{leaveRequestsOldsEntity.pmstatus}</dd>
          <dt>
            <span id="isbench">Isbench</span>
          </dt>
          <dd>{leaveRequestsOldsEntity.isbench ? 'true' : 'false'}</dd>
          <dt>
            <span id="isescalated">Isescalated</span>
          </dt>
          <dd>{leaveRequestsOldsEntity.isescalated ? 'true' : 'false'}</dd>
          <dt>
            <span id="iscommented">Iscommented</span>
          </dt>
          <dd>{leaveRequestsOldsEntity.iscommented ? 'true' : 'false'}</dd>
          <dt>
            <span id="isreminded">Isreminded</span>
          </dt>
          <dd>{leaveRequestsOldsEntity.isreminded ? 'true' : 'false'}</dd>
          <dt>
            <span id="isnotified">Isnotified</span>
          </dt>
          <dd>{leaveRequestsOldsEntity.isnotified ? 'true' : 'false'}</dd>
          <dt>
            <span id="isremindedhr">Isremindedhr</span>
          </dt>
          <dd>{leaveRequestsOldsEntity.isremindedhr ? 'true' : 'false'}</dd>
          <dt>
            <span id="isdm">Isdm</span>
          </dt>
          <dd>{leaveRequestsOldsEntity.isdm ? 'true' : 'false'}</dd>
          <dt>Leavetype</dt>
          <dd>{leaveRequestsOldsEntity.leavetype ? leaveRequestsOldsEntity.leavetype.id : ''}</dd>
          <dt>Manager</dt>
          <dd>{leaveRequestsOldsEntity.manager ? leaveRequestsOldsEntity.manager.id : ''}</dd>
          <dt>Employee</dt>
          <dd>{leaveRequestsOldsEntity.employee ? leaveRequestsOldsEntity.employee.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/leave-requests-olds" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/leave-requests-olds/${leaveRequestsOldsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeaveRequestsOldsDetail;
