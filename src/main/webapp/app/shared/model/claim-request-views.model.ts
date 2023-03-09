import dayjs from 'dayjs';
import { IEmployees } from 'app/shared/model/employees.model';

export interface IClaimRequestViews {
  id?: number;
  costcenter?: string | null;
  comments?: string | null;
  amountreleased?: number | null;
  designation?: string | null;
  department?: string | null;
  location?: string | null;
  manager?: string | null;
  createdat?: string | null;
  updatedat?: string;
  employee?: IEmployees | null;
}

export const defaultValue: Readonly<IClaimRequestViews> = {};
