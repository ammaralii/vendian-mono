import dayjs from 'dayjs';
import { IEmployees } from 'app/shared/model/employees.model';

export interface IEmploymentHistory {
  id?: number;
  positiontitle?: string;
  companyname?: string;
  grade?: string | null;
  jobdescription?: string | null;
  city?: string | null;
  country?: string | null;
  startdate?: string;
  enddate?: string;
  createdat?: string | null;
  updatedat?: string | null;
  reasonforleaving?: string | null;
  grosssalaryContentType?: string | null;
  grosssalary?: string | null;
  employee?: IEmployees;
}

export const defaultValue: Readonly<IEmploymentHistory> = {};
