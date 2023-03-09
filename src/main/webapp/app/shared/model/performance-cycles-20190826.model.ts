import dayjs from 'dayjs';

export interface IPerformanceCycles20190826 {
  id?: number;
  month?: boolean | null;
  year?: boolean | null;
  totalresources?: boolean | null;
  pmreviewed?: boolean | null;
  archreviewed?: boolean | null;
  startdate?: string | null;
  enddate?: string | null;
  isactive?: boolean | null;
  createdat?: string | null;
  updatedat?: string | null;
  projectcount?: boolean | null;
  criteria?: number | null;
  notificationsent?: boolean | null;
  duedate?: string | null;
  initiationdate?: string | null;
}

export const defaultValue: Readonly<IPerformanceCycles20190826> = {
  month: false,
  year: false,
  totalresources: false,
  pmreviewed: false,
  archreviewed: false,
  isactive: false,
  projectcount: false,
  notificationsent: false,
};
