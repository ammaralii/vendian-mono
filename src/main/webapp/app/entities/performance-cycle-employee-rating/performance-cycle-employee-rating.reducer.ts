import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending, isRejected } from '@reduxjs/toolkit';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { IPerformanceCycleEmployeeRating, defaultValue } from 'app/shared/model/performance-cycle-employee-rating.model';

const initialState: EntityState<IPerformanceCycleEmployeeRating> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const apiUrl = 'api/performance-cycle-employee-ratings';

// Actions

export const getEntities = createAsyncThunk(
  'performanceCycleEmployeeRating/fetch_entity_list',
  async ({ page, size, sort }: IQueryParams) => {
    const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}&` : '?'}cacheBuster=${new Date().getTime()}`;
    return axios.get<IPerformanceCycleEmployeeRating[]>(requestUrl);
  }
);

export const getEntity = createAsyncThunk(
  'performanceCycleEmployeeRating/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<IPerformanceCycleEmployeeRating>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

export const createEntity = createAsyncThunk(
  'performanceCycleEmployeeRating/create_entity',
  async (entity: IPerformanceCycleEmployeeRating, thunkAPI) => {
    const result = await axios.post<IPerformanceCycleEmployeeRating>(apiUrl, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const updateEntity = createAsyncThunk(
  'performanceCycleEmployeeRating/update_entity',
  async (entity: IPerformanceCycleEmployeeRating, thunkAPI) => {
    const result = await axios.put<IPerformanceCycleEmployeeRating>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const partialUpdateEntity = createAsyncThunk(
  'performanceCycleEmployeeRating/partial_update_entity',
  async (entity: IPerformanceCycleEmployeeRating, thunkAPI) => {
    const result = await axios.patch<IPerformanceCycleEmployeeRating>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const deleteEntity = createAsyncThunk(
  'performanceCycleEmployeeRating/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    const result = await axios.delete<IPerformanceCycleEmployeeRating>(requestUrl);
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

// slice

export const PerformanceCycleEmployeeRatingSlice = createEntitySlice({
  name: 'performanceCycleEmployeeRating',
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

export const { reset } = PerformanceCycleEmployeeRatingSlice.actions;

// Reducer
export default PerformanceCycleEmployeeRatingSlice.reducer;
