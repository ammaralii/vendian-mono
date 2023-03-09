import dayjs from 'dayjs';
import { ILeaveTypesOlds } from 'app/shared/model/leave-types-olds.model';
import { IEmployees } from 'app/shared/model/employees.model';

export interface ILeaveRequestsOlds {
  id?: number;
  startdate?: string | null;
  enddate?: string | null;
  requesternote?: string | null;
  managernote?: string | null;
  currentstatus?: string | null;
  leavescanceled?: boolean | null;
  requestdate?: string | null;
  linkstring?: string | null;
  linkused?: boolean | null;
  createdat?: string;
  updatedat?: string;
  ishalfday?: boolean | null;
  actiondate?: string | null;
  lmstatus?: string | null;
  pmstatus?: string | null;
  isbench?: boolean | null;
  isescalated?: boolean | null;
  iscommented?: boolean | null;
  isreminded?: boolean | null;
  isnotified?: boolean | null;
  isremindedhr?: boolean | null;
  isdm?: boolean | null;
  leavetype?: ILeaveTypesOlds | null;
  manager?: IEmployees | null;
  employee?: IEmployees | null;
}

export const defaultValue: Readonly<ILeaveRequestsOlds> = {
  leavescanceled: false,
  linkused: false,
  ishalfday: false,
  isbench: false,
  isescalated: false,
  iscommented: false,
  isreminded: false,
  isnotified: false,
  isremindedhr: false,
  isdm: false,
};
