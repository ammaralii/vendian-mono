import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PcRatingAttributesCategories from './pc-rating-attributes-categories';
import PcRatingAttributesCategoriesDetail from './pc-rating-attributes-categories-detail';
import PcRatingAttributesCategoriesUpdate from './pc-rating-attributes-categories-update';
import PcRatingAttributesCategoriesDeleteDialog from './pc-rating-attributes-categories-delete-dialog';

const PcRatingAttributesCategoriesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PcRatingAttributesCategories />} />
    <Route path="new" element={<PcRatingAttributesCategoriesUpdate />} />
    <Route path=":id">
      <Route index element={<PcRatingAttributesCategoriesDetail />} />
      <Route path="edit" element={<PcRatingAttributesCategoriesUpdate />} />
      <Route path="delete" element={<PcRatingAttributesCategoriesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PcRatingAttributesCategoriesRoutes;
