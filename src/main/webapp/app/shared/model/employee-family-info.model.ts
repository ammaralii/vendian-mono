import dayjs from 'dayjs';
import { IEmployees } from 'app/shared/model/employees.model';

export interface IEmployeeFamilyInfo {
  id?: number;
  fullname?: string;
  relationship?: string;
  contactno?: string | null;
  email?: string | null;
  dob?: string | null;
  registeredinmedical?: boolean | null;
  cnicContentType?: string | null;
  cnic?: string | null;
  createdat?: string | null;
  updatedat?: string | null;
  medicalpolicyno?: string | null;
  gender?: string;
  employee?: IEmployees;
}

export const defaultValue: Readonly<IEmployeeFamilyInfo> = {
  registeredinmedical: false,
};
