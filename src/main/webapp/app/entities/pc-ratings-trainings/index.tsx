import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PcRatingsTrainings from './pc-ratings-trainings';
import PcRatingsTrainingsDetail from './pc-ratings-trainings-detail';
import PcRatingsTrainingsUpdate from './pc-ratings-trainings-update';
import PcRatingsTrainingsDeleteDialog from './pc-ratings-trainings-delete-dialog';

const PcRatingsTrainingsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PcRatingsTrainings />} />
    <Route path="new" element={<PcRatingsTrainingsUpdate />} />
    <Route path=":id">
      <Route index element={<PcRatingsTrainingsDetail />} />
      <Route path="edit" element={<PcRatingsTrainingsUpdate />} />
      <Route path="delete" element={<PcRatingsTrainingsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PcRatingsTrainingsRoutes;
