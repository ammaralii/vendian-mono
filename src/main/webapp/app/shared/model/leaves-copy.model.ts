import dayjs from 'dayjs';

export interface ILeavesCopy {
  id?: number;
  annualtotal?: number | null;
  annualused?: number | null;
  annualadjustments?: number | null;
  casualtotal?: number | null;
  casualused?: number | null;
  sicktotal?: number | null;
  sickused?: number | null;
  annualtotalnextyear?: number | null;
  annualusednextyear?: number | null;
  casualtotalnextyear?: number | null;
  casualusednextyear?: number | null;
  sicktotalnextyear?: number | null;
  sickusednextyear?: number | null;
  carryforward?: number | null;
  createdat?: string;
  updatedat?: string;
}

export const defaultValue: Readonly<ILeavesCopy> = {};
