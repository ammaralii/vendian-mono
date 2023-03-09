import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './user-goal-rater-attributes.reducer';

export const UserGoalRaterAttributesDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const userGoalRaterAttributesEntity = useAppSelector(state => state.userGoalRaterAttributes.entity);
  const updateSuccess = useAppSelector(state => state.userGoalRaterAttributes.updateSuccess);

  const handleClose = () => {
    navigate('/user-goal-rater-attributes' + location.search);
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(userGoalRaterAttributesEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="userGoalRaterAttributesDeleteDialogHeading">
        Confirm delete operation
      </ModalHeader>
      <ModalBody id="vendianMonoApp.userGoalRaterAttributes.delete.question">
        Are you sure you want to delete User Goal Rater Attributes {userGoalRaterAttributesEntity.id}?
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Cancel
        </Button>
        <Button id="jhi-confirm-delete-userGoalRaterAttributes" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp; Delete
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default UserGoalRaterAttributesDeleteDialog;
