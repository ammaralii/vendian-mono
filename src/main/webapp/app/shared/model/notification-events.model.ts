import dayjs from 'dayjs';
import { INotificationMergeFields } from 'app/shared/model/notification-merge-fields.model';
import { INotificationTemplates } from 'app/shared/model/notification-templates.model';

export interface INotificationEvents {
  id?: number;
  name?: string;
  effDate?: string;
  createdAt?: string;
  updatedAt?: string;
  endDate?: string | null;
  version?: number;
  notificationmergefieldsNotificationevents?: INotificationMergeFields[] | null;
  notificationtemplatesNotificationevents?: INotificationTemplates[] | null;
}

export const defaultValue: Readonly<INotificationEvents> = {};
