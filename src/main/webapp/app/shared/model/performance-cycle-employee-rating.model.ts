import dayjs from 'dayjs';
import { IHrPerformanceCycles } from 'app/shared/model/hr-performance-cycles.model';
import { IEmployees } from 'app/shared/model/employees.model';
import { IPcRatings } from 'app/shared/model/pc-ratings.model';
import { IUserRatingsRemarks } from 'app/shared/model/user-ratings-remarks.model';

export interface IPerformanceCycleEmployeeRating {
  id?: number;
  ratingContentType?: string | null;
  rating?: string | null;
  commentContentType?: string | null;
  comment?: string | null;
  createdAt?: string;
  updatedAt?: string;
  deletedAt?: string | null;
  version?: number;
  performancecycle?: IHrPerformanceCycles;
  employee?: IEmployees;
  manager?: IEmployees;
  pcratingsPcemployeeratings?: IPcRatings[] | null;
  userratingsremarksPcemployeeratings?: IUserRatingsRemarks[] | null;
}

export const defaultValue: Readonly<IPerformanceCycleEmployeeRating> = {};
