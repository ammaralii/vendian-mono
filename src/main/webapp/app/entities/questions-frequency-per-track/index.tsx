import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import QuestionsFrequencyPerTrack from './questions-frequency-per-track';
import QuestionsFrequencyPerTrackDetail from './questions-frequency-per-track-detail';
import QuestionsFrequencyPerTrackUpdate from './questions-frequency-per-track-update';
import QuestionsFrequencyPerTrackDeleteDialog from './questions-frequency-per-track-delete-dialog';

const QuestionsFrequencyPerTrackRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<QuestionsFrequencyPerTrack />} />
    <Route path="new" element={<QuestionsFrequencyPerTrackUpdate />} />
    <Route path=":id">
      <Route index element={<QuestionsFrequencyPerTrackDetail />} />
      <Route path="edit" element={<QuestionsFrequencyPerTrackUpdate />} />
      <Route path="delete" element={<QuestionsFrequencyPerTrackDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default QuestionsFrequencyPerTrackRoutes;
