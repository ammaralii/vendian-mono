import dayjs from 'dayjs';
import { IDesignations } from 'app/shared/model/designations.model';
import { IEmployees } from 'app/shared/model/employees.model';
import { IPerformanceCycleEmployeeRating } from 'app/shared/model/performance-cycle-employee-rating.model';

export interface IUserRatingsRemarks {
  id?: number;
  isPromotion?: boolean | null;
  strength?: string | null;
  areaOfImprovement?: string | null;
  promotionJustification?: string | null;
  newGrade?: string | null;
  isRedesignation?: boolean | null;
  recommendedSalary?: number | null;
  status?: string | null;
  createdAt?: string;
  updatedAt?: string;
  deletedAt?: string | null;
  version?: number;
  otherComments?: string | null;
  designationAfterPromotion?: IDesignations | null;
  designationAfterRedesignation?: IDesignations | null;
  rater?: IEmployees;
  pcEmployeeRating?: IPerformanceCycleEmployeeRating;
}

export const defaultValue: Readonly<IUserRatingsRemarks> = {
  isPromotion: false,
  isRedesignation: false,
};
