import dayjs from 'dayjs';
import { IEmployees } from 'app/shared/model/employees.model';

export interface ILocations {
  id?: number;
  name?: string;
  createdat?: string;
  updatedat?: string;
  deletedat?: string | null;
  employeesLocations?: IEmployees[] | null;
}

export const defaultValue: Readonly<ILocations> = {};
