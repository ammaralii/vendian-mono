import dayjs from 'dayjs';
import { IQualificationTypes } from 'app/shared/model/qualification-types.model';
import { IEmployees } from 'app/shared/model/employees.model';

export interface IEmployeeEducation {
  id?: number;
  institute?: string;
  major?: string | null;
  degree?: string | null;
  value?: string | null;
  city?: string | null;
  province?: string | null;
  country?: string | null;
  datefrom?: string | null;
  dateto?: string | null;
  createdat?: string | null;
  updatedat?: string | null;
  qualificationtype?: IQualificationTypes;
  employee?: IEmployees;
}

export const defaultValue: Readonly<IEmployeeEducation> = {};
