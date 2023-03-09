import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import QuestionsFrequencyPerClientTrack from './questions-frequency-per-client-track';
import QuestionsFrequencyPerClientTrackDetail from './questions-frequency-per-client-track-detail';
import QuestionsFrequencyPerClientTrackUpdate from './questions-frequency-per-client-track-update';
import QuestionsFrequencyPerClientTrackDeleteDialog from './questions-frequency-per-client-track-delete-dialog';

const QuestionsFrequencyPerClientTrackRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<QuestionsFrequencyPerClientTrack />} />
    <Route path="new" element={<QuestionsFrequencyPerClientTrackUpdate />} />
    <Route path=":id">
      <Route index element={<QuestionsFrequencyPerClientTrackDetail />} />
      <Route path="edit" element={<QuestionsFrequencyPerClientTrackUpdate />} />
      <Route path="delete" element={<QuestionsFrequencyPerClientTrackDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default QuestionsFrequencyPerClientTrackRoutes;
