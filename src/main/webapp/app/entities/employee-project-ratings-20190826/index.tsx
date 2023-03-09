import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EmployeeProjectRatings20190826 from './employee-project-ratings-20190826';
import EmployeeProjectRatings20190826Detail from './employee-project-ratings-20190826-detail';
import EmployeeProjectRatings20190826Update from './employee-project-ratings-20190826-update';
import EmployeeProjectRatings20190826DeleteDialog from './employee-project-ratings-20190826-delete-dialog';

const EmployeeProjectRatings20190826Routes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EmployeeProjectRatings20190826 />} />
    <Route path="new" element={<EmployeeProjectRatings20190826Update />} />
    <Route path=":id">
      <Route index element={<EmployeeProjectRatings20190826Detail />} />
      <Route path="edit" element={<EmployeeProjectRatings20190826Update />} />
      <Route path="delete" element={<EmployeeProjectRatings20190826DeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EmployeeProjectRatings20190826Routes;
