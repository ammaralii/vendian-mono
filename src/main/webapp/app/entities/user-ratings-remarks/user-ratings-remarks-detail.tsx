import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-ratings-remarks.reducer';

export const UserRatingsRemarksDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const userRatingsRemarksEntity = useAppSelector(state => state.userRatingsRemarks.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userRatingsRemarksDetailsHeading">User Ratings Remarks</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{userRatingsRemarksEntity.id}</dd>
          <dt>
            <span id="isPromotion">Is Promotion</span>
          </dt>
          <dd>{userRatingsRemarksEntity.isPromotion ? 'true' : 'false'}</dd>
          <dt>
            <span id="strength">Strength</span>
          </dt>
          <dd>{userRatingsRemarksEntity.strength}</dd>
          <dt>
            <span id="areaOfImprovement">Area Of Improvement</span>
          </dt>
          <dd>{userRatingsRemarksEntity.areaOfImprovement}</dd>
          <dt>
            <span id="promotionJustification">Promotion Justification</span>
          </dt>
          <dd>{userRatingsRemarksEntity.promotionJustification}</dd>
          <dt>
            <span id="newGrade">New Grade</span>
          </dt>
          <dd>{userRatingsRemarksEntity.newGrade}</dd>
          <dt>
            <span id="isRedesignation">Is Redesignation</span>
          </dt>
          <dd>{userRatingsRemarksEntity.isRedesignation ? 'true' : 'false'}</dd>
          <dt>
            <span id="recommendedSalary">Recommended Salary</span>
          </dt>
          <dd>{userRatingsRemarksEntity.recommendedSalary}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{userRatingsRemarksEntity.status}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {userRatingsRemarksEntity.createdAt ? (
              <TextFormat value={userRatingsRemarksEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>
            {userRatingsRemarksEntity.updatedAt ? (
              <TextFormat value={userRatingsRemarksEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="deletedAt">Deleted At</span>
          </dt>
          <dd>
            {userRatingsRemarksEntity.deletedAt ? (
              <TextFormat value={userRatingsRemarksEntity.deletedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="version">Version</span>
          </dt>
          <dd>{userRatingsRemarksEntity.version}</dd>
          <dt>
            <span id="otherComments">Other Comments</span>
          </dt>
          <dd>{userRatingsRemarksEntity.otherComments}</dd>
          <dt>Designation After Promotion</dt>
          <dd>{userRatingsRemarksEntity.designationAfterPromotion ? userRatingsRemarksEntity.designationAfterPromotion.id : ''}</dd>
          <dt>Designation After Redesignation</dt>
          <dd>{userRatingsRemarksEntity.designationAfterRedesignation ? userRatingsRemarksEntity.designationAfterRedesignation.id : ''}</dd>
          <dt>Rater</dt>
          <dd>{userRatingsRemarksEntity.rater ? userRatingsRemarksEntity.rater.id : ''}</dd>
          <dt>Pc Employee Rating</dt>
          <dd>{userRatingsRemarksEntity.pcEmployeeRating ? userRatingsRemarksEntity.pcEmployeeRating.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-ratings-remarks" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-ratings-remarks/${userRatingsRemarksEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserRatingsRemarksDetail;
