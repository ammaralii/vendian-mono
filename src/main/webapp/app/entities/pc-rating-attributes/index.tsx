import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PcRatingAttributes from './pc-rating-attributes';
import PcRatingAttributesDetail from './pc-rating-attributes-detail';
import PcRatingAttributesUpdate from './pc-rating-attributes-update';
import PcRatingAttributesDeleteDialog from './pc-rating-attributes-delete-dialog';

const PcRatingAttributesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PcRatingAttributes />} />
    <Route path="new" element={<PcRatingAttributesUpdate />} />
    <Route path=":id">
      <Route index element={<PcRatingAttributesDetail />} />
      <Route path="edit" element={<PcRatingAttributesUpdate />} />
      <Route path="delete" element={<PcRatingAttributesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PcRatingAttributesRoutes;
