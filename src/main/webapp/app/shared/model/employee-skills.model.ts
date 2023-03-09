import dayjs from 'dayjs';
import { IEmployees } from 'app/shared/model/employees.model';
import { ISkills } from 'app/shared/model/skills.model';

export interface IEmployeeSkills {
  id?: number;
  createdat?: string;
  updatedat?: string;
  expertise?: number | null;
  employee?: IEmployees | null;
  skill?: ISkills | null;
}

export const defaultValue: Readonly<IEmployeeSkills> = {};
