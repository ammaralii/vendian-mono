import dayjs from 'dayjs';
import { IEmployees } from 'app/shared/model/employees.model';
import { IProjects } from 'app/shared/model/projects.model';
import { ITracks } from 'app/shared/model/tracks.model';
import { IQuestions } from 'app/shared/model/questions.model';

export interface IInterviews {
  id?: number;
  result?: string | null;
  clientcomments?: string | null;
  lmcomments?: string | null;
  scheduledat?: string | null;
  createdat?: string;
  updatedat?: string;
  deletedat?: string | null;
  employee?: IEmployees;
  project?: IProjects;
  track?: ITracks;
  questionsInterviews?: IQuestions[] | null;
}

export const defaultValue: Readonly<IInterviews> = {};
