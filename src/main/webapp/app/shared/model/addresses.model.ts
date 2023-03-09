import dayjs from 'dayjs';
import { IEmployeeAddresses } from 'app/shared/model/employee-addresses.model';

export interface IAddresses {
  id?: number;
  address?: string;
  city?: string | null;
  province?: string | null;
  country?: string | null;
  postalcode?: string | null;
  isdeleted?: boolean | null;
  createdat?: string | null;
  updatedat?: string | null;
  employeeaddressesAddresses?: IEmployeeAddresses[] | null;
}

export const defaultValue: Readonly<IAddresses> = {
  isdeleted: false,
};
