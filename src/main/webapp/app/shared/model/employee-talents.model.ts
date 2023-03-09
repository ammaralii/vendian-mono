import dayjs from 'dayjs';
import { IEmployees } from 'app/shared/model/employees.model';

export interface IEmployeeTalents {
  id?: number;
  criticalposition?: boolean | null;
  highpotential?: boolean | null;
  successorfor?: string | null;
  criticalexperience?: boolean | null;
  promotionreadiness?: string | null;
  leadershipqualities?: string | null;
  careerambitions?: string | null;
  createdat?: string | null;
  updatedat?: string | null;
  employee?: IEmployees;
}

export const defaultValue: Readonly<IEmployeeTalents> = {
  criticalposition: false,
  highpotential: false,
  criticalexperience: false,
};
