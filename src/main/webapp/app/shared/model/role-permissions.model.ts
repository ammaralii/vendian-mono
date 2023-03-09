import dayjs from 'dayjs';
import { IRoles } from 'app/shared/model/roles.model';
import { IPermissions } from 'app/shared/model/permissions.model';

export interface IRolePermissions {
  id?: number;
  createdat?: string;
  updatedat?: string;
  role?: IRoles | null;
  permission?: IPermissions | null;
}

export const defaultValue: Readonly<IRolePermissions> = {};
