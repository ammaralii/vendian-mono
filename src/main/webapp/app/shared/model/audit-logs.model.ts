import dayjs from 'dayjs';

export interface IAuditLogs {
  id?: number;
  event?: string;
  eventTime?: string;
  description?: string | null;
  oldChange?: string | null;
  newChange?: string | null;
  createdAt?: string;
  updatedAt?: string;
  version?: number;
}

export const defaultValue: Readonly<IAuditLogs> = {};
