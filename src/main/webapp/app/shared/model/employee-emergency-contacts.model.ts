import dayjs from 'dayjs';
import { IEmployees } from 'app/shared/model/employees.model';

export interface IEmployeeEmergencyContacts {
  id?: number;
  fullname?: string;
  relationship?: string;
  contactno?: string;
  createdat?: string | null;
  updatedat?: string | null;
  employee?: IEmployees;
}

export const defaultValue: Readonly<IEmployeeEmergencyContacts> = {};
