import dayjs from 'dayjs';
import { IEmployees } from 'app/shared/model/employees.model';
import { IWorkLogDetails } from 'app/shared/model/work-log-details.model';

export interface IWorkLogs {
  id?: number;
  date?: string | null;
  mood?: number | null;
  createdat?: string;
  updatedat?: string;
  employee?: IEmployees | null;
  worklogdetailsWorklogs?: IWorkLogDetails[] | null;
}

export const defaultValue: Readonly<IWorkLogs> = {};
