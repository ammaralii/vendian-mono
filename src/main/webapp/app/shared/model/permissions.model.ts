import dayjs from 'dayjs';
import { IRolePermissions } from 'app/shared/model/role-permissions.model';

export interface IPermissions {
  id?: number;
  name?: string | null;
  description?: string | null;
  groupName?: string | null;
  isactive?: boolean | null;
  createdat?: string;
  updatedat?: string;
  rolepermissionsPermissions?: IRolePermissions[] | null;
}

export const defaultValue: Readonly<IPermissions> = {
  isactive: false,
};
