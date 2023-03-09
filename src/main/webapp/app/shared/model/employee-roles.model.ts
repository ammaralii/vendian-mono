import dayjs from 'dayjs';
import { IRoles } from 'app/shared/model/roles.model';

export interface IEmployeeRoles {
  id?: number;
  createdat?: string;
  updatedat?: string;
  employeeid?: number | null;
  role?: IRoles | null;
}

export const defaultValue: Readonly<IEmployeeRoles> = {};
