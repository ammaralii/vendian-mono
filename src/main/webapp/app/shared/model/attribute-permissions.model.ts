import dayjs from 'dayjs';

export interface IAttributePermissions {
  id?: number;
  method?: string;
  route?: string;
  responsepermissions?: string | null;
  requestpermissions?: string | null;
  createdat?: string;
  updatedat?: string;
}

export const defaultValue: Readonly<IAttributePermissions> = {};
