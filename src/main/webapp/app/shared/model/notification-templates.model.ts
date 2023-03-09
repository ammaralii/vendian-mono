import dayjs from 'dayjs';
import { INotificationEvents } from 'app/shared/model/notification-events.model';
import { INotificationSentEmailLogs } from 'app/shared/model/notification-sent-email-logs.model';

export interface INotificationTemplates {
  id?: number;
  name?: string;
  type?: string;
  subject?: string | null;
  templateContentType?: string;
  template?: string;
  effDate?: string;
  createdAt?: string;
  updatedAt?: string;
  endDate?: string | null;
  version?: number;
  notificationEvent?: INotificationEvents;
  notificationsentemaillogsNotificationtemplates?: INotificationSentEmailLogs[] | null;
}

export const defaultValue: Readonly<INotificationTemplates> = {};
