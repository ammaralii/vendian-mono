import dayjs from 'dayjs';

export interface IZLeaveRequests {
  id?: number;
  action?: string | null;
  userid?: string | null;
  managerid?: string | null;
  requestparams?: string | null;
  responseparams?: string | null;
  createdat?: string;
  updatedat?: string;
}

export const defaultValue: Readonly<IZLeaveRequests> = {};
