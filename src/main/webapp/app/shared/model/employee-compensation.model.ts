import dayjs from 'dayjs';
import { IEmployees } from 'app/shared/model/employees.model';
import { IReasons } from 'app/shared/model/reasons.model';
import { ICompensationBenefits } from 'app/shared/model/compensation-benefits.model';

export interface IEmployeeCompensation {
  id?: number;
  amountContentType?: string;
  amount?: string;
  date?: string;
  ecReasonContentType?: string | null;
  ecReason?: string | null;
  type?: string;
  commitmentuntil?: string | null;
  comments?: string | null;
  createdat?: string | null;
  updatedat?: string | null;
  reasoncomments?: string | null;
  employee?: IEmployees;
  reason?: IReasons | null;
  compensationbenefitsEmployeecompensations?: ICompensationBenefits[] | null;
}

export const defaultValue: Readonly<IEmployeeCompensation> = {};
