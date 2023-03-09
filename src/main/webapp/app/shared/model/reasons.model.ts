import dayjs from 'dayjs';
import { IEmployeeCompensation } from 'app/shared/model/employee-compensation.model';

export interface IReasons {
  id?: number;
  name?: string;
  isdefault?: boolean | null;
  createdat?: string | null;
  updatedat?: string | null;
  employeecompensationReasons?: IEmployeeCompensation[] | null;
}

export const defaultValue: Readonly<IReasons> = {
  isdefault: false,
};
