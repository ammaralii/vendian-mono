import dayjs from 'dayjs';
import { IEmployeeRoles } from 'app/shared/model/employee-roles.model';
import { IEmployees } from 'app/shared/model/employees.model';
import { IRolePermissions } from 'app/shared/model/role-permissions.model';

export interface IRoles {
  id?: number;
  role?: string | null;
  createdat?: string;
  updatedat?: string;
  employeerolesRoles?: IEmployeeRoles[] | null;
  employeesRoles?: IEmployees[] | null;
  rolepermissionsRoles?: IRolePermissions[] | null;
}

export const defaultValue: Readonly<IRoles> = {};
