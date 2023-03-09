import dayjs from 'dayjs';
import { IBenefits } from 'app/shared/model/benefits.model';
import { IEmployeeCompensation } from 'app/shared/model/employee-compensation.model';

export interface ICompensationBenefits {
  id?: number;
  amount?: number | null;
  isdeleted?: boolean | null;
  createdat?: string | null;
  updatedat?: string | null;
  benefit?: IBenefits;
  employeecompensation?: IEmployeeCompensation;
}

export const defaultValue: Readonly<ICompensationBenefits> = {
  isdeleted: false,
};
