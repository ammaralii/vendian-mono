import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CompensationBenefits from './compensation-benefits';
import CompensationBenefitsDetail from './compensation-benefits-detail';
import CompensationBenefitsUpdate from './compensation-benefits-update';
import CompensationBenefitsDeleteDialog from './compensation-benefits-delete-dialog';

const CompensationBenefitsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CompensationBenefits />} />
    <Route path="new" element={<CompensationBenefitsUpdate />} />
    <Route path=":id">
      <Route index element={<CompensationBenefitsDetail />} />
      <Route path="edit" element={<CompensationBenefitsUpdate />} />
      <Route path="delete" element={<CompensationBenefitsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CompensationBenefitsRoutes;
