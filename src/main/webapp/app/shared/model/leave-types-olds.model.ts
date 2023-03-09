import dayjs from 'dayjs';
import { ILeaveRequestsOlds } from 'app/shared/model/leave-requests-olds.model';

export interface ILeaveTypesOlds {
  id?: number;
  name?: string | null;
  isactive?: boolean | null;
  createdat?: string;
  updatedat?: string;
  leaverequestsoldsLeavetypes?: ILeaveRequestsOlds[] | null;
}

export const defaultValue: Readonly<ILeaveTypesOlds> = {
  isactive: false,
};
