import dayjs from 'dayjs';
import { IEmployees } from 'app/shared/model/employees.model';

export interface IEmployeeBirthdays {
  id?: number;
  birthdayDetails?: string | null;
  year?: number;
  createdat?: string | null;
  updatedat?: string | null;
  employee?: IEmployees | null;
}

export const defaultValue: Readonly<IEmployeeBirthdays> = {};
