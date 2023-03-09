import dayjs from 'dayjs';
import { IEmployees } from 'app/shared/model/employees.model';

export interface IEmployeeProfileHistoryLogs {
  id?: number;
  tablename?: string;
  rowid?: number;
  eventtype?: string;
  fieldsContentType?: string | null;
  fields?: string | null;
  updatedbyid?: number;
  activityid?: string;
  createdat?: string | null;
  updatedat?: string | null;
  category?: string;
  employee?: IEmployees;
}

export const defaultValue: Readonly<IEmployeeProfileHistoryLogs> = {};
