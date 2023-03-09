import dayjs from 'dayjs';
import { IPcStatuses } from 'app/shared/model/pc-statuses.model';
import { IPerformanceCycleEmployeeRating } from 'app/shared/model/performance-cycle-employee-rating.model';
import { IEmployees } from 'app/shared/model/employees.model';
import { IPcRaterAttributes } from 'app/shared/model/pc-rater-attributes.model';
import { IPcRatingsTrainings } from 'app/shared/model/pc-ratings-trainings.model';
import { IUserGoalRaterAttributes } from 'app/shared/model/user-goal-rater-attributes.model';

export interface IPcRatings {
  id?: number;
  ratingContentType?: string | null;
  rating?: string | null;
  commentContentType?: string | null;
  comment?: string | null;
  stausDate?: string | null;
  dueDate?: string | null;
  freeze?: boolean | null;
  includeInFinalRating?: boolean | null;
  createdAt?: string;
  updatedAt?: string;
  deletedAt?: string | null;
  version?: number;
  status?: IPcStatuses | null;
  pcemployeerating?: IPerformanceCycleEmployeeRating;
  employee?: IEmployees;
  pcraterattributesRatings?: IPcRaterAttributes[] | null;
  pcratingstrainingsRatings?: IPcRatingsTrainings[] | null;
  usergoalraterattributesRatings?: IUserGoalRaterAttributes[] | null;
}

export const defaultValue: Readonly<IPcRatings> = {
  freeze: false,
  includeInFinalRating: false,
};
