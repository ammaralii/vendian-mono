import dayjs from 'dayjs';
import { IDealResourceSkills } from 'app/shared/model/deal-resource-skills.model';
import { IEmployeeSkills } from 'app/shared/model/employee-skills.model';

export interface ISkills {
  id?: number;
  name?: string | null;
  createdat?: string;
  updatedat?: string;
  dealresourceskillsSkills?: IDealResourceSkills[] | null;
  employeeskillsSkills?: IEmployeeSkills[] | null;
}

export const defaultValue: Readonly<ISkills> = {};
