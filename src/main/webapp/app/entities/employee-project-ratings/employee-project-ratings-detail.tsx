import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employee-project-ratings.reducer';

export const EmployeeProjectRatingsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const employeeProjectRatingsEntity = useAppSelector(state => state.employeeProjectRatings.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeeProjectRatingsDetailsHeading">Employee Project Ratings</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{employeeProjectRatingsEntity.id}</dd>
          <dt>
            <span id="createdat">Createdat</span>
          </dt>
          <dd>
            {employeeProjectRatingsEntity.createdat ? (
              <TextFormat value={employeeProjectRatingsEntity.createdat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedat">Updatedat</span>
          </dt>
          <dd>
            {employeeProjectRatingsEntity.updatedat ? (
              <TextFormat value={employeeProjectRatingsEntity.updatedat} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="pmquality">Pmquality</span>
          </dt>
          <dd>
            {employeeProjectRatingsEntity.pmquality ? (
              <div>
                {employeeProjectRatingsEntity.pmqualityContentType ? (
                  <a onClick={openFile(employeeProjectRatingsEntity.pmqualityContentType, employeeProjectRatingsEntity.pmquality)}>
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatingsEntity.pmqualityContentType}, {byteSize(employeeProjectRatingsEntity.pmquality)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="pmownership">Pmownership</span>
          </dt>
          <dd>
            {employeeProjectRatingsEntity.pmownership ? (
              <div>
                {employeeProjectRatingsEntity.pmownershipContentType ? (
                  <a onClick={openFile(employeeProjectRatingsEntity.pmownershipContentType, employeeProjectRatingsEntity.pmownership)}>
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatingsEntity.pmownershipContentType}, {byteSize(employeeProjectRatingsEntity.pmownership)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="pmskill">Pmskill</span>
          </dt>
          <dd>
            {employeeProjectRatingsEntity.pmskill ? (
              <div>
                {employeeProjectRatingsEntity.pmskillContentType ? (
                  <a onClick={openFile(employeeProjectRatingsEntity.pmskillContentType, employeeProjectRatingsEntity.pmskill)}>
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatingsEntity.pmskillContentType}, {byteSize(employeeProjectRatingsEntity.pmskill)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="pmethics">Pmethics</span>
          </dt>
          <dd>
            {employeeProjectRatingsEntity.pmethics ? (
              <div>
                {employeeProjectRatingsEntity.pmethicsContentType ? (
                  <a onClick={openFile(employeeProjectRatingsEntity.pmethicsContentType, employeeProjectRatingsEntity.pmethics)}>
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatingsEntity.pmethicsContentType}, {byteSize(employeeProjectRatingsEntity.pmethics)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="pmefficiency">Pmefficiency</span>
          </dt>
          <dd>
            {employeeProjectRatingsEntity.pmefficiency ? (
              <div>
                {employeeProjectRatingsEntity.pmefficiencyContentType ? (
                  <a onClick={openFile(employeeProjectRatingsEntity.pmefficiencyContentType, employeeProjectRatingsEntity.pmefficiency)}>
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatingsEntity.pmefficiencyContentType}, {byteSize(employeeProjectRatingsEntity.pmefficiency)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="pmfreeze">Pmfreeze</span>
          </dt>
          <dd>
            {employeeProjectRatingsEntity.pmfreeze ? (
              <div>
                {employeeProjectRatingsEntity.pmfreezeContentType ? (
                  <a onClick={openFile(employeeProjectRatingsEntity.pmfreezeContentType, employeeProjectRatingsEntity.pmfreeze)}>
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatingsEntity.pmfreezeContentType}, {byteSize(employeeProjectRatingsEntity.pmfreeze)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="archfreeze">Archfreeze</span>
          </dt>
          <dd>
            {employeeProjectRatingsEntity.archfreeze ? (
              <div>
                {employeeProjectRatingsEntity.archfreezeContentType ? (
                  <a onClick={openFile(employeeProjectRatingsEntity.archfreezeContentType, employeeProjectRatingsEntity.archfreeze)}>
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatingsEntity.archfreezeContentType}, {byteSize(employeeProjectRatingsEntity.archfreeze)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="pmcomment">Pmcomment</span>
          </dt>
          <dd>
            {employeeProjectRatingsEntity.pmcomment ? (
              <div>
                {employeeProjectRatingsEntity.pmcommentContentType ? (
                  <a onClick={openFile(employeeProjectRatingsEntity.pmcommentContentType, employeeProjectRatingsEntity.pmcomment)}>
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatingsEntity.pmcommentContentType}, {byteSize(employeeProjectRatingsEntity.pmcomment)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="archquality">Archquality</span>
          </dt>
          <dd>
            {employeeProjectRatingsEntity.archquality ? (
              <div>
                {employeeProjectRatingsEntity.archqualityContentType ? (
                  <a onClick={openFile(employeeProjectRatingsEntity.archqualityContentType, employeeProjectRatingsEntity.archquality)}>
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatingsEntity.archqualityContentType}, {byteSize(employeeProjectRatingsEntity.archquality)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="archownership">Archownership</span>
          </dt>
          <dd>
            {employeeProjectRatingsEntity.archownership ? (
              <div>
                {employeeProjectRatingsEntity.archownershipContentType ? (
                  <a onClick={openFile(employeeProjectRatingsEntity.archownershipContentType, employeeProjectRatingsEntity.archownership)}>
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatingsEntity.archownershipContentType}, {byteSize(employeeProjectRatingsEntity.archownership)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="archskill">Archskill</span>
          </dt>
          <dd>
            {employeeProjectRatingsEntity.archskill ? (
              <div>
                {employeeProjectRatingsEntity.archskillContentType ? (
                  <a onClick={openFile(employeeProjectRatingsEntity.archskillContentType, employeeProjectRatingsEntity.archskill)}>
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatingsEntity.archskillContentType}, {byteSize(employeeProjectRatingsEntity.archskill)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="archethics">Archethics</span>
          </dt>
          <dd>
            {employeeProjectRatingsEntity.archethics ? (
              <div>
                {employeeProjectRatingsEntity.archethicsContentType ? (
                  <a onClick={openFile(employeeProjectRatingsEntity.archethicsContentType, employeeProjectRatingsEntity.archethics)}>
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatingsEntity.archethicsContentType}, {byteSize(employeeProjectRatingsEntity.archethics)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="archefficiency">Archefficiency</span>
          </dt>
          <dd>
            {employeeProjectRatingsEntity.archefficiency ? (
              <div>
                {employeeProjectRatingsEntity.archefficiencyContentType ? (
                  <a
                    onClick={openFile(employeeProjectRatingsEntity.archefficiencyContentType, employeeProjectRatingsEntity.archefficiency)}
                  >
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatingsEntity.archefficiencyContentType}, {byteSize(employeeProjectRatingsEntity.archefficiency)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="archcomment">Archcomment</span>
          </dt>
          <dd>
            {employeeProjectRatingsEntity.archcomment ? (
              <div>
                {employeeProjectRatingsEntity.archcommentContentType ? (
                  <a onClick={openFile(employeeProjectRatingsEntity.archcommentContentType, employeeProjectRatingsEntity.archcomment)}>
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatingsEntity.archcommentContentType}, {byteSize(employeeProjectRatingsEntity.archcomment)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="archcodequality">Archcodequality</span>
          </dt>
          <dd>
            {employeeProjectRatingsEntity.archcodequality ? (
              <div>
                {employeeProjectRatingsEntity.archcodequalityContentType ? (
                  <a
                    onClick={openFile(
                      employeeProjectRatingsEntity.archcodequalityContentType,
                      employeeProjectRatingsEntity.archcodequality
                    )}
                  >
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatingsEntity.archcodequalityContentType}, {byteSize(employeeProjectRatingsEntity.archcodequality)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="archdocumentation">Archdocumentation</span>
          </dt>
          <dd>
            {employeeProjectRatingsEntity.archdocumentation ? (
              <div>
                {employeeProjectRatingsEntity.archdocumentationContentType ? (
                  <a
                    onClick={openFile(
                      employeeProjectRatingsEntity.archdocumentationContentType,
                      employeeProjectRatingsEntity.archdocumentation
                    )}
                  >
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatingsEntity.archdocumentationContentType}, {byteSize(employeeProjectRatingsEntity.archdocumentation)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="archcollaboration">Archcollaboration</span>
          </dt>
          <dd>
            {employeeProjectRatingsEntity.archcollaboration ? (
              <div>
                {employeeProjectRatingsEntity.archcollaborationContentType ? (
                  <a
                    onClick={openFile(
                      employeeProjectRatingsEntity.archcollaborationContentType,
                      employeeProjectRatingsEntity.archcollaboration
                    )}
                  >
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatingsEntity.archcollaborationContentType}, {byteSize(employeeProjectRatingsEntity.archcollaboration)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="pmdocumentation">Pmdocumentation</span>
          </dt>
          <dd>
            {employeeProjectRatingsEntity.pmdocumentation ? (
              <div>
                {employeeProjectRatingsEntity.pmdocumentationContentType ? (
                  <a
                    onClick={openFile(
                      employeeProjectRatingsEntity.pmdocumentationContentType,
                      employeeProjectRatingsEntity.pmdocumentation
                    )}
                  >
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatingsEntity.pmdocumentationContentType}, {byteSize(employeeProjectRatingsEntity.pmdocumentation)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="pmcollaboration">Pmcollaboration</span>
          </dt>
          <dd>
            {employeeProjectRatingsEntity.pmcollaboration ? (
              <div>
                {employeeProjectRatingsEntity.pmcollaborationContentType ? (
                  <a
                    onClick={openFile(
                      employeeProjectRatingsEntity.pmcollaborationContentType,
                      employeeProjectRatingsEntity.pmcollaboration
                    )}
                  >
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeProjectRatingsEntity.pmcollaborationContentType}, {byteSize(employeeProjectRatingsEntity.pmcollaboration)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>Projectarchitect</dt>
          <dd>{employeeProjectRatingsEntity.projectarchitect ? employeeProjectRatingsEntity.projectarchitect.id : ''}</dd>
          <dt>Projectmanager</dt>
          <dd>{employeeProjectRatingsEntity.projectmanager ? employeeProjectRatingsEntity.projectmanager.id : ''}</dd>
          <dt>Employee</dt>
          <dd>{employeeProjectRatingsEntity.employee ? employeeProjectRatingsEntity.employee.id : ''}</dd>
          <dt>Projectcycle</dt>
          <dd>{employeeProjectRatingsEntity.projectcycle ? employeeProjectRatingsEntity.projectcycle.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee-project-ratings" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee-project-ratings/${employeeProjectRatingsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmployeeProjectRatingsDetail;
