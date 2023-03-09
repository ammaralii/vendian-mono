import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BusinessUnits from './business-units';
import BusinessUnitsDetail from './business-units-detail';
import BusinessUnitsUpdate from './business-units-update';
import BusinessUnitsDeleteDialog from './business-units-delete-dialog';

const BusinessUnitsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BusinessUnits />} />
    <Route path="new" element={<BusinessUnitsUpdate />} />
    <Route path=":id">
      <Route index element={<BusinessUnitsDetail />} />
      <Route path="edit" element={<BusinessUnitsUpdate />} />
      <Route path="delete" element={<BusinessUnitsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BusinessUnitsRoutes;
