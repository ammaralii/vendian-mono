import dayjs from 'dayjs';
import { IProjectCycles } from 'app/shared/model/project-cycles.model';
import { IRatings } from 'app/shared/model/ratings.model';

export interface IPerformanceCycles {
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
  projectcycles?: IProjectCycles[] | null;
  employeeratings?: IRatings[] | null;
}

export const defaultValue: Readonly<IPerformanceCycles> = {
  month: false,
  year: false,
  totalresources: false,
  pmreviewed: false,
  archreviewed: false,
  isactive: false,
  projectcount: false,
  notificationsent: false,
};
