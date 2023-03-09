import dayjs from 'dayjs';
import { INotificationTemplates } from 'app/shared/model/notification-templates.model';
import { IEmployees } from 'app/shared/model/employees.model';

export interface INotificationSentEmailLogs {
  id?: number;
  email?: string;
  createdAt?: string;
  updatedAt?: string;
  deletedAt?: string | null;
  version?: number;
  notificationTemplate?: INotificationTemplates;
  recipient?: IEmployees;
}

export const defaultValue: Readonly<INotificationSentEmailLogs> = {};
