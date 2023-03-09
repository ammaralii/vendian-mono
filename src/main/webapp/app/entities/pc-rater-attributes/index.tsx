import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PcRaterAttributes from './pc-rater-attributes';
import PcRaterAttributesDetail from './pc-rater-attributes-detail';
import PcRaterAttributesUpdate from './pc-rater-attributes-update';
import PcRaterAttributesDeleteDialog from './pc-rater-attributes-delete-dialog';

const PcRaterAttributesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PcRaterAttributes />} />
    <Route path="new" element={<PcRaterAttributesUpdate />} />
    <Route path=":id">
      <Route index element={<PcRaterAttributesDetail />} />
      <Route path="edit" element={<PcRaterAttributesUpdate />} />
      <Route path="delete" element={<PcRaterAttributesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PcRaterAttributesRoutes;
