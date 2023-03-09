import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending, isRejected } from '@reduxjs/toolkit';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { IQuestionsFrequencyPerClientTrack, defaultValue } from 'app/shared/model/questions-frequency-per-client-track.model';

const initialState: EntityState<IQuestionsFrequencyPerClientTrack> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const apiUrl = 'api/questions-frequency-per-client-tracks';

// Actions

export const getEntities = createAsyncThunk(
  'questionsFrequencyPerClientTrack/fetch_entity_list',
  async ({ page, size, sort }: IQueryParams) => {
    const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}&` : '?'}cacheBuster=${new Date().getTime()}`;
    return axios.get<IQuestionsFrequencyPerClientTrack[]>(requestUrl);
  }
);

export const getEntity = createAsyncThunk(
  'questionsFrequencyPerClientTrack/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<IQuestionsFrequencyPerClientTrack>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

export const createEntity = createAsyncThunk(
  'questionsFrequencyPerClientTrack/create_entity',
  async (entity: IQuestionsFrequencyPerClientTrack, thunkAPI) => {
    const result = await axios.post<IQuestionsFrequencyPerClientTrack>(apiUrl, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const updateEntity = createAsyncThunk(
  'questionsFrequencyPerClientTrack/update_entity',
  async (entity: IQuestionsFrequencyPerClientTrack, thunkAPI) => {
    const result = await axios.put<IQuestionsFrequencyPerClientTrack>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const partialUpdateEntity = createAsyncThunk(
  'questionsFrequencyPerClientTrack/partial_update_entity',
  async (entity: IQuestionsFrequencyPerClientTrack, thunkAPI) => {
    const result = await axios.patch<IQuestionsFrequencyPerClientTrack>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const deleteEntity = createAsyncThunk(
  'questionsFrequencyPerClientTrack/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    const result = await axios.delete<IQuestionsFrequencyPerClientTrack>(requestUrl);
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

// slice

export const QuestionsFrequencyPerClientTrackSlice = createEntitySlice({
  name: 'questionsFrequencyPerClientTrack',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getEntity.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })
      .addCase(deleteEntity.fulfilled, state => {
        state.updating = false;
        state.updateSuccess = true;
        state.entity = {};
      })
      .addMatcher(isFulfilled(getEntities), (state, action) => {
        const { data, headers } = action.payload;

        return {
          ...state,
          loading: false,
          entities: data,
          totalItems: parseInt(headers['x-total-count'], 10),
        };
      })
      .addMatcher(isFulfilled(createEntity, updateEntity, partialUpdateEntity), (state, action) => {
        state.updating = false;
        state.loading = false;
        state.updateSuccess = true;
        state.entity = action.payload.data;
      })
      .addMatcher(isPending(getEntities, getEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isPending(createEntity, updateEntity, partialUpdateEntity, deleteEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.updating = true;
      });
  },
});

export const { reset } = QuestionsFrequencyPerClientTrackSlice.actions;

// Reducer
export default QuestionsFrequencyPerClientTrackSlice.reducer;
