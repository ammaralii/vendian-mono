import dayjs from 'dayjs';
import { IWorkLogs } from 'app/shared/model/work-logs.model';
import { IProjects } from 'app/shared/model/projects.model';

export interface IWorkLogDetails {
  id?: number;
  percentage?: string | null;
  hours?: number | null;
  createdat?: string;
  updatedat?: string;
  worklog?: IWorkLogs | null;
  project?: IProjects | null;
}

export const defaultValue: Readonly<IWorkLogDetails> = {};
