import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Documents from './documents';
import DocumentsDetail from './documents-detail';
import DocumentsUpdate from './documents-update';
import DocumentsDeleteDialog from './documents-delete-dialog';

const DocumentsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Documents />} />
    <Route path="new" element={<DocumentsUpdate />} />
    <Route path=":id">
      <Route index element={<DocumentsDetail />} />
      <Route path="edit" element={<DocumentsUpdate />} />
      <Route path="delete" element={<DocumentsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DocumentsRoutes;
