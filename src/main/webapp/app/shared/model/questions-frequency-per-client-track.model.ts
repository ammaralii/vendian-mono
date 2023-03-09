import dayjs from 'dayjs';
import { IProjects } from 'app/shared/model/projects.model';
import { ITracks } from 'app/shared/model/tracks.model';

export interface IQuestionsFrequencyPerClientTrack {
  id?: number;
  question?: string;
  frequency?: number;
  createdat?: string;
  updatedat?: string;
  project?: IProjects | null;
  track?: ITracks | null;
}

export const defaultValue: Readonly<IQuestionsFrequencyPerClientTrack> = {};
