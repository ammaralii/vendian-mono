import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employee-project-ratings-20190826.reducer';

export const EmployeeProjectRatings20190826Detail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const employeeProjectRatings20190826Entity = useAppSelector(state => state.employeeProjectRatings20190826.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeeProjectRatings20190826DetailsHeading">Employee Project Ratings 20190826</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{employeeProjectRatings20190826Entity.id}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {employeeProjectRatings20190826Entity.createdat ? (
              <TextFormat value={employeeProjectRatings20190826Entity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {employeeProjectRatings20190826Entity.updatedat ? (
              <TextFormat value={employeeProjectRatings20190826Entity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="pmquality">Pmquality</span>
          </dt>
          <dd>
            {employeeProjectRatings20190826Entity.pmquality ? (
              <div>
                {employeeProjectRatings20190826Entity.pmqualityContentType ? (
                  <a
                    onClick={openFile(
                      employeeProjectRatings20190826Entity.pmqualityContentType,
                      employeeProjectRatings20190826Entity.pmquality
                    )}
                  >
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatings20190826Entity.pmqualityContentType}, {byteSize(employeeProjectRatings20190826Entity.pmquality)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="pmownership">Pmownership</span>
          </dt>
          <dd>
            {employeeProjectRatings20190826Entity.pmownership ? (
              <div>
                {employeeProjectRatings20190826Entity.pmownershipContentType ? (
                  <a
                    onClick={openFile(
                      employeeProjectRatings20190826Entity.pmownershipContentType,
                      employeeProjectRatings20190826Entity.pmownership
                    )}
                  >
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatings20190826Entity.pmownershipContentType},{' '}
                  {byteSize(employeeProjectRatings20190826Entity.pmownership)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="pmskill">Pmskill</span>
          </dt>
          <dd>
            {employeeProjectRatings20190826Entity.pmskill ? (
              <div>
                {employeeProjectRatings20190826Entity.pmskillContentType ? (
                  <a
                    onClick={openFile(
                      employeeProjectRatings20190826Entity.pmskillContentType,
                      employeeProjectRatings20190826Entity.pmskill
                    )}
                  >
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatings20190826Entity.pmskillContentType}, {byteSize(employeeProjectRatings20190826Entity.pmskill)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="pmethics">Pmethics</span>
          </dt>
          <dd>
            {employeeProjectRatings20190826Entity.pmethics ? (
              <div>
                {employeeProjectRatings20190826Entity.pmethicsContentType ? (
                  <a
                    onClick={openFile(
                      employeeProjectRatings20190826Entity.pmethicsContentType,
                      employeeProjectRatings20190826Entity.pmethics
                    )}
                  >
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatings20190826Entity.pmethicsContentType}, {byteSize(employeeProjectRatings20190826Entity.pmethics)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="pmefficiency">Pmefficiency</span>
          </dt>
          <dd>
            {employeeProjectRatings20190826Entity.pmefficiency ? (
              <div>
                {employeeProjectRatings20190826Entity.pmefficiencyContentType ? (
                  <a
                    onClick={openFile(
                      employeeProjectRatings20190826Entity.pmefficiencyContentType,
                      employeeProjectRatings20190826Entity.pmefficiency
                    )}
                  >
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatings20190826Entity.pmefficiencyContentType},{' '}
                  {byteSize(employeeProjectRatings20190826Entity.pmefficiency)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="pmfreeze">Pmfreeze</span>
          </dt>
          <dd>
            {employeeProjectRatings20190826Entity.pmfreeze ? (
              <div>
                {employeeProjectRatings20190826Entity.pmfreezeContentType ? (
                  <a
                    onClick={openFile(
                      employeeProjectRatings20190826Entity.pmfreezeContentType,
                      employeeProjectRatings20190826Entity.pmfreeze
                    )}
                  >
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatings20190826Entity.pmfreezeContentType}, {byteSize(employeeProjectRatings20190826Entity.pmfreeze)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="archfreeze">Archfreeze</span>
          </dt>
          <dd>
            {employeeProjectRatings20190826Entity.archfreeze ? (
              <div>
                {employeeProjectRatings20190826Entity.archfreezeContentType ? (
                  <a
                    onClick={openFile(
                      employeeProjectRatings20190826Entity.archfreezeContentType,
                      employeeProjectRatings20190826Entity.archfreeze
                    )}
                  >
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatings20190826Entity.archfreezeContentType}, {byteSize(employeeProjectRatings20190826Entity.archfreeze)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="pmcomment">Pmcomment</span>
          </dt>
          <dd>
            {employeeProjectRatings20190826Entity.pmcomment ? (
              <div>
                {employeeProjectRatings20190826Entity.pmcommentContentType ? (
                  <a
                    onClick={openFile(
                      employeeProjectRatings20190826Entity.pmcommentContentType,
                      employeeProjectRatings20190826Entity.pmcomment
                    )}
                  >
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatings20190826Entity.pmcommentContentType}, {byteSize(employeeProjectRatings20190826Entity.pmcomment)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="archquality">Archquality</span>
          </dt>
          <dd>
            {employeeProjectRatings20190826Entity.archquality ? (
              <div>
                {employeeProjectRatings20190826Entity.archqualityContentType ? (
                  <a
                    onClick={openFile(
                      employeeProjectRatings20190826Entity.archqualityContentType,
                      employeeProjectRatings20190826Entity.archquality
                    )}
                  >
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatings20190826Entity.archqualityContentType},{' '}
                  {byteSize(employeeProjectRatings20190826Entity.archquality)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="archownership">Archownership</span>
          </dt>
          <dd>
            {employeeProjectRatings20190826Entity.archownership ? (
              <div>
                {employeeProjectRatings20190826Entity.archownershipContentType ? (
                  <a
                    onClick={openFile(
                      employeeProjectRatings20190826Entity.archownershipContentType,
                      employeeProjectRatings20190826Entity.archownership
                    )}
                  >
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatings20190826Entity.archownershipContentType},{' '}
                  {byteSize(employeeProjectRatings20190826Entity.archownership)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="archskill">Archskill</span>
          </dt>
          <dd>
            {employeeProjectRatings20190826Entity.archskill ? (
              <div>
                {employeeProjectRatings20190826Entity.archskillContentType ? (
                  <a
                    onClick={openFile(
                      employeeProjectRatings20190826Entity.archskillContentType,
                      employeeProjectRatings20190826Entity.archskill
                    )}
                  >
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatings20190826Entity.archskillContentType}, {byteSize(employeeProjectRatings20190826Entity.archskill)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="archethics">Archethics</span>
          </dt>
          <dd>
            {employeeProjectRatings20190826Entity.archethics ? (
              <div>
                {employeeProjectRatings20190826Entity.archethicsContentType ? (
                  <a
                    onClick={openFile(
                      employeeProjectRatings20190826Entity.archethicsContentType,
                      employeeProjectRatings20190826Entity.archethics
                    )}
                  >
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatings20190826Entity.archethicsContentType}, {byteSize(employeeProjectRatings20190826Entity.archethics)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="archefficiency">Archefficiency</span>
          </dt>
          <dd>
            {employeeProjectRatings20190826Entity.archefficiency ? (
              <div>
                {employeeProjectRatings20190826Entity.archefficiencyContentType ? (
                  <a
                    onClick={openFile(
                      employeeProjectRatings20190826Entity.archefficiencyContentType,
                      employeeProjectRatings20190826Entity.archefficiency
                    )}
                  >
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatings20190826Entity.archefficiencyContentType},{' '}
                  {byteSize(employeeProjectRatings20190826Entity.archefficiency)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="archcomment">Archcomment</span>
          </dt>
          <dd>
            {employeeProjectRatings20190826Entity.archcomment ? (
              <div>
                {employeeProjectRatings20190826Entity.archcommentContentType ? (
                  <a
                    onClick={openFile(
                      employeeProjectRatings20190826Entity.archcommentContentType,
                      employeeProjectRatings20190826Entity.archcomment
                    )}
                  >
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatings20190826Entity.archcommentContentType},{' '}
                  {byteSize(employeeProjectRatings20190826Entity.archcomment)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="projectarchitectid">Projectarchitectid</span>
          </dt>
          <dd>{employeeProjectRatings20190826Entity.projectarchitectid}</dd>
          <dt>
            <span id="projectmanagerid">Projectmanagerid</span>
          </dt>
          <dd>{employeeProjectRatings20190826Entity.projectmanagerid}</dd>
          <dt>
            <span id="employeeid">Employeeid</span>
          </dt>
          <dd>{employeeProjectRatings20190826Entity.employeeid}</dd>
          <dt>
            <span id="projectcycleid">Projectcycleid</span>
          </dt>
          <dd>{employeeProjectRatings20190826Entity.projectcycleid}</dd>
        </dl>
        <Button tag={Link} to="/employee-project-ratings-20190826" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/employee-project-ratings-20190826/${employeeProjectRatings20190826Entity.id}/edit`}
          replace
          color="primary"
        >
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmployeeProjectRatings20190826Detail;
