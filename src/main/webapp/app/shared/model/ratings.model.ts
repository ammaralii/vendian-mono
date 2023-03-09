import dayjs from 'dayjs';
import { IEmployees } from 'app/shared/model/employees.model';
import { IRatingAttributes } from 'app/shared/model/rating-attributes.model';
import { IPerformanceCycles } from 'app/shared/model/performance-cycles.model';
import { IProjectCycles } from 'app/shared/model/project-cycles.model';

export interface IRatings {
  id?: number;
  rateeid?: number | null;
  rateetype?: string | null;
  duedate?: string | null;
  freezeContentType?: string | null;
  freeze?: string | null;
  createdat?: string;
  updatedat?: string;
  deletedat?: string | null;
  rater?: IEmployees | null;
  ratingattributesRatings?: IRatingAttributes[] | null;
  performancecycles?: IPerformanceCycles[] | null;
  projectcycles?: IProjectCycles[] | null;
}

export const defaultValue: Readonly<IRatings> = {};
