import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employee-profile-history-logs.reducer';

export const EmployeeProfileHistoryLogsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const employeeProfileHistoryLogsEntity = useAppSelector(state => state.employeeProfileHistoryLogs.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeeProfileHistoryLogsDetailsHeading">Employee Profile History Logs</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{employeeProfileHistoryLogsEntity.id}</dd>
          <dt>
            <span id="tablename">Tablename</span>
          </dt>
          <dd>{employeeProfileHistoryLogsEntity.tablename}</dd>
          <dt>
            <span id="rowid">Rowid</span>
          </dt>
          <dd>{employeeProfileHistoryLogsEntity.rowid}</dd>
          <dt>
            <span id="eventtype">Eventtype</span>
          </dt>
          <dd>{employeeProfileHistoryLogsEntity.eventtype}</dd>
          <dt>
            <span id="fields">Fields</span>
          </dt>
          <dd>
            {employeeProfileHistoryLogsEntity.fields ? (
              <div>
                {employeeProfileHistoryLogsEntity.fieldsContentType ? (
                  <a onClick={openFile(employeeProfileHistoryLogsEntity.fieldsContentType, employeeProfileHistoryLogsEntity.fields)}>
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProfileHistoryLogsEntity.fieldsContentType}, {byteSize(employeeProfileHistoryLogsEntity.fields)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="updatedbyid">Updatedbyid</span>
          </dt>
          <dd>{employeeProfileHistoryLogsEntity.updatedbyid}</dd>
          <dt>
            <span id="activityid">Activityid</span>
          </dt>
          <dd>{employeeProfileHistoryLogsEntity.activityid}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {employeeProfileHistoryLogsEntity.createdat ? (
              <TextFormat value={employeeProfileHistoryLogsEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {employeeProfileHistoryLogsEntity.updatedat ? (
              <TextFormat value={employeeProfileHistoryLogsEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="category">Category</span>
          </dt>
          <dd>{employeeProfileHistoryLogsEntity.category}</dd>
          <dt>Employee</dt>
          <dd>{employeeProfileHistoryLogsEntity.employee ? employeeProfileHistoryLogsEntity.employee.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee-profile-history-logs" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee-profile-history-logs/${employeeProfileHistoryLogsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmployeeProfileHistoryLogsDetail;
