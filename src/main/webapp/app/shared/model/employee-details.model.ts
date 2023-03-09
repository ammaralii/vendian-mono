import dayjs from 'dayjs';
import { IEmployees } from 'app/shared/model/employees.model';

export interface IEmployeeDetails {
  id?: number;
  religion?: string | null;
  maritalstatus?: string | null;
  cnicContentType?: string | null;
  cnic?: string | null;
  cnicexpiryContentType?: string | null;
  cnicexpiry?: string | null;
  bloodgroup?: string | null;
  taxreturnfilerContentType?: string | null;
  taxreturnfiler?: string | null;
  passportnoContentType?: string | null;
  passportno?: string | null;
  passportexpiryContentType?: string | null;
  passportexpiry?: string | null;
  createdat?: string | null;
  updatedat?: string | null;
  totaltenure?: string | null;
  employee?: IEmployees;
}

export const defaultValue: Readonly<IEmployeeDetails> = {};
