import dayjs from 'dayjs';
import { IAddresses } from 'app/shared/model/addresses.model';
import { IEmployees } from 'app/shared/model/employees.model';

export interface IEmployeeAddresses {
  id?: number;
  isdeleted?: boolean | null;
  createdat?: string | null;
  updatedat?: string | null;
  type?: string;
  address?: IAddresses;
  employee?: IEmployees;
}

export const defaultValue: Readonly<IEmployeeAddresses> = {
  isdeleted: false,
};
