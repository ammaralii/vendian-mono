import dayjs from 'dayjs';
import { IEmployees } from 'app/shared/model/employees.model';
import { ILeaveDetails } from 'app/shared/model/leave-details.model';

export interface ILeaves {
  id?: number;
  effDate?: string;
  createdAt?: string;
  updatedAt?: string;
  endDate?: string | null;
  version?: number;
  user?: IEmployees;
  leavedetailsLeaves?: ILeaveDetails[] | null;
}

export const defaultValue: Readonly<ILeaves> = {};
