import dayjs from 'dayjs';

export interface IProjectCycles20190826 {
  id?: number;
  isactive?: boolean | null;
  createdat?: string | null;
  updatedat?: string | null;
  performancecycleid?: number | null;
  projectid?: number | null;
  allowedafterduedateby?: number | null;
  allowedafterduedateat?: string | null;
  duedate?: string | null;
}

export const defaultValue: Readonly<IProjectCycles20190826> = {
  isactive: false,
};
