import dayjs from 'dayjs';
import { INotificationEvents } from 'app/shared/model/notification-events.model';

export interface INotificationMergeFields {
  id?: number;
  field?: string;
  effDate?: string;
  createdAt?: string;
  updatedAt?: string;
  endDate?: string | null;
  version?: number;
  notificationEvent?: INotificationEvents;
}

export const defaultValue: Readonly<INotificationMergeFields> = {};
