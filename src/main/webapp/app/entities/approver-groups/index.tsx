import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ApproverGroups from './approver-groups';
import ApproverGroupsDetail from './approver-groups-detail';
import ApproverGroupsUpdate from './approver-groups-update';
import ApproverGroupsDeleteDialog from './approver-groups-delete-dialog';

const ApproverGroupsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ApproverGroups />} />
    <Route path="new" element={<ApproverGroupsUpdate />} />
    <Route path=":id">
      <Route index element={<ApproverGroupsDetail />} />
      <Route path="edit" element={<ApproverGroupsUpdate />} />
      <Route path="delete" element={<ApproverGroupsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ApproverGroupsRoutes;
