import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './leave-status-work-flows.reducer';

export const LeaveStatusWorkFlowsDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const leaveStatusWorkFlowsEntity = useAppSelector(state => state.leaveStatusWorkFlows.entity);
  const updateSuccess = useAppSelector(state => state.leaveStatusWorkFlows.updateSuccess);

  const handleClose = () => {
    navigate('/leave-status-work-flows' + location.search);
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(leaveStatusWorkFlowsEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="leaveStatusWorkFlowsDeleteDialogHeading">
        Confirm delete operation
      </ModalHeader>
      <ModalBody id="vendianMonoApp.leaveStatusWorkFlows.delete.question">
        Are you sure you want to delete Leave Status Work Flows {leaveStatusWorkFlowsEntity.id}?
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Cancel
        </Button>
        <Button id="jhi-confirm-delete-leaveStatusWorkFlows" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp; Delete
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default LeaveStatusWorkFlowsDeleteDialog;
