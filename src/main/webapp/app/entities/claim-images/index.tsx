import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ClaimImages from './claim-images';
import ClaimImagesDetail from './claim-images-detail';
import ClaimImagesUpdate from './claim-images-update';
import ClaimImagesDeleteDialog from './claim-images-delete-dialog';

const ClaimImagesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ClaimImages />} />
    <Route path="new" element={<ClaimImagesUpdate />} />
    <Route path=":id">
      <Route index element={<ClaimImagesDetail />} />
      <Route path="edit" element={<ClaimImagesUpdate />} />
      <Route path="delete" element={<ClaimImagesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ClaimImagesRoutes;
