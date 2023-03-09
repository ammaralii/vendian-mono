import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DesignationJobDescriptions from './designation-job-descriptions';
import DesignationJobDescriptionsDetail from './designation-job-descriptions-detail';
import DesignationJobDescriptionsUpdate from './designation-job-descriptions-update';
import DesignationJobDescriptionsDeleteDialog from './designation-job-descriptions-delete-dialog';

const DesignationJobDescriptionsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DesignationJobDescriptions />} />
    <Route path="new" element={<DesignationJobDescriptionsUpdate />} />
    <Route path=":id">
      <Route index element={<DesignationJobDescriptionsDetail />} />
      <Route path="edit" element={<DesignationJobDescriptionsUpdate />} />
      <Route path="delete" element={<DesignationJobDescriptionsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DesignationJobDescriptionsRoutes;
