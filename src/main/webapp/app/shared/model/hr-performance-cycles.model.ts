import dayjs from 'dayjs';
import { IPerformanceCycleEmployeeRating } from 'app/shared/model/performance-cycle-employee-rating.model';
import { IUserRatings } from 'app/shared/model/user-ratings.model';

export interface IHrPerformanceCycles {
  id?: number;
  title?: string;
  freeze?: boolean | null;
  dueDate?: string | null;
  startDate?: string | null;
  endDate?: string | null;
  createdAt?: string;
  updatedAt?: string;
  deletedAt?: string | null;
  version?: number;
  performancecycleemployeeratingPerformancecycles?: IPerformanceCycleEmployeeRating[] | null;
  userratingsPerformancecycles?: IUserRatings[] | null;
}

export const defaultValue: Readonly<IHrPerformanceCycles> = {
  freeze: false,
};
