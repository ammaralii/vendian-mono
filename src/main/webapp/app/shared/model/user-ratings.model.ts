import dayjs from 'dayjs';
import { IEmployees } from 'app/shared/model/employees.model';
import { IHrPerformanceCycles } from 'app/shared/model/hr-performance-cycles.model';

export interface IUserRatings {
  id?: number;
  rating?: string;
  comment?: string | null;
  createdAt?: string;
  updatedAt?: string;
  deletedAt?: string | null;
  version?: number;
  user?: IEmployees;
  reviewManager?: IEmployees;
  performanceCycle?: IHrPerformanceCycles;
}

export const defaultValue: Readonly<IUserRatings> = {};
