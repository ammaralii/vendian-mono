import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employee-comments.reducer';

export const EmployeeCommentsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const employeeCommentsEntity = useAppSelector(state => state.employeeComments.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeeCommentsDetailsHeading">Employee Comments</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{employeeCommentsEntity.id}</dd>
          <dt>
            <span id="title">Title</span>
          </dt>
          <dd>
            {employeeCommentsEntity.title ? (
              <div>
                {employeeCommentsEntity.titleContentType ? (
                  <a onClick={openFile(employeeCommentsEntity.titleContentType, employeeCommentsEntity.title)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {employeeCommentsEntity.titleContentType}, {byteSize(employeeCommentsEntity.title)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="content">Content</span>
          </dt>
          <dd>
            {employeeCommentsEntity.content ? (
              <div>
                {employeeCommentsEntity.contentContentType ? (
                  <a onClick={openFile(employeeCommentsEntity.contentContentType, employeeCommentsEntity.content)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {employeeCommentsEntity.contentContentType}, {byteSize(employeeCommentsEntity.content)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="dated">Dated</span>
          </dt>
          <dd>
            {employeeCommentsEntity.dated ? (
              <div>
                {employeeCommentsEntity.datedContentType ? (
                  <a onClick={openFile(employeeCommentsEntity.datedContentType, employeeCommentsEntity.dated)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {employeeCommentsEntity.datedContentType}, {byteSize(employeeCommentsEntity.dated)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {employeeCommentsEntity.createdat ? (
              <TextFormat value={employeeCommentsEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {employeeCommentsEntity.updatedat ? (
              <TextFormat value={employeeCommentsEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Document</dt>
          <dd>{employeeCommentsEntity.document ? employeeCommentsEntity.document.id : ''}</dd>
          <dt>Commenter</dt>
          <dd>{employeeCommentsEntity.commenter ? employeeCommentsEntity.commenter.id : ''}</dd>
          <dt>Employee</dt>
          <dd>{employeeCommentsEntity.employee ? employeeCommentsEntity.employee.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee-comments" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee-comments/${employeeCommentsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmployeeCommentsDetail;
