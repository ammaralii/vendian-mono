import dayjs from 'dayjs';
import { ITracks } from 'app/shared/model/tracks.model';

export interface IQuestionsFrequencyPerTrack {
  id?: number;
  question?: string;
  frequency?: number;
  createdat?: string;
  updatedat?: string;
  track?: ITracks;
}

export const defaultValue: Readonly<IQuestionsFrequencyPerTrack> = {};
