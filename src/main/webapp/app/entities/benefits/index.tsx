import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Benefits from './benefits';
import BenefitsDetail from './benefits-detail';
import BenefitsUpdate from './benefits-update';
import BenefitsDeleteDialog from './benefits-delete-dialog';

const BenefitsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Benefits />} />
    <Route path="new" element={<BenefitsUpdate />} />
    <Route path=":id">
      <Route index element={<BenefitsDetail />} />
      <Route path="edit" element={<BenefitsUpdate />} />
      <Route path="delete" element={<BenefitsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BenefitsRoutes;
