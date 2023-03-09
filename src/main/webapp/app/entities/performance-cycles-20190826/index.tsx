import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PerformanceCycles20190826 from './performance-cycles-20190826';
import PerformanceCycles20190826Detail from './performance-cycles-20190826-detail';
import PerformanceCycles20190826Update from './performance-cycles-20190826-update';
import PerformanceCycles20190826DeleteDialog from './performance-cycles-20190826-delete-dialog';

const PerformanceCycles20190826Routes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PerformanceCycles20190826 />} />
    <Route path="new" element={<PerformanceCycles20190826Update />} />
    <Route path=":id">
      <Route index element={<PerformanceCycles20190826Detail />} />
      <Route path="edit" element={<PerformanceCycles20190826Update />} />
      <Route path="delete" element={<PerformanceCycles20190826DeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PerformanceCycles20190826Routes;
