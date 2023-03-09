import dayjs from 'dayjs';
import { IEmployeeEducation } from 'app/shared/model/employee-education.model';

export interface IQualificationTypes {
  id?: number;
  name?: string;
  createdat?: string | null;
  updatedat?: string | null;
  employeeeducationQualificationtypes?: IEmployeeEducation[] | null;
}

export const defaultValue: Readonly<IQualificationTypes> = {};
