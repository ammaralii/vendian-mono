import dayjs from 'dayjs';
import { IEmployees } from 'app/shared/model/employees.model';

export interface IEmployeeCertificates {
  id?: number;
  name?: string;
  certificateno?: string | null;
  issuingbody?: string | null;
  date?: string;
  createdat?: string | null;
  updatedat?: string | null;
  validtill?: string | null;
  certificatecompetency?: string | null;
  deletedat?: string | null;
  employee?: IEmployees;
}

export const defaultValue: Readonly<IEmployeeCertificates> = {};
