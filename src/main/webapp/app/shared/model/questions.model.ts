import dayjs from 'dayjs';
import { IInterviews } from 'app/shared/model/interviews.model';
import { IProjects } from 'app/shared/model/projects.model';
import { ITracks } from 'app/shared/model/tracks.model';

export interface IQuestions {
  id?: number;
  text?: string | null;
  answer?: string | null;
  createdat?: string;
  updatedat?: string;
  deletedat?: string | null;
  cleaneduptext?: string | null;
  interview?: IInterviews | null;
  project?: IProjects | null;
  track?: ITracks | null;
}

export const defaultValue: Readonly<IQuestions> = {};
