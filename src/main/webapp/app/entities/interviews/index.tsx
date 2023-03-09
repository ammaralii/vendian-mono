import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Interviews from './interviews';
import InterviewsDetail from './interviews-detail';
import InterviewsUpdate from './interviews-update';
import InterviewsDeleteDialog from './interviews-delete-dialog';

const InterviewsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Interviews />} />
    <Route path="new" element={<InterviewsUpdate />} />
    <Route path=":id">
      <Route index element={<InterviewsDetail />} />
      <Route path="edit" element={<InterviewsUpdate />} />
      <Route path="delete" element={<InterviewsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InterviewsRoutes;
