import dayjs from 'dayjs';
import { IEmployeeJobInfo } from 'app/shared/model/employee-job-info.model';
import { IEmployees } from 'app/shared/model/employees.model';
import { ITracks } from 'app/shared/model/tracks.model';

export interface ICompetencies {
  id?: number;
  name?: string;
  createdat?: string;
  updatedat?: string;
  employeejobinfoCompetencies?: IEmployeeJobInfo[] | null;
  employeesCompetencies?: IEmployees[] | null;
  tracksCompetencies?: ITracks[] | null;
}

export const defaultValue: Readonly<ICompetencies> = {};
