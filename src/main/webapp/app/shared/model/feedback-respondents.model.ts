import dayjs from 'dayjs';
import { IEmployees } from 'app/shared/model/employees.model';
import { IFeedbackRequests } from 'app/shared/model/feedback-requests.model';
import { IFeedbackEmails } from 'app/shared/model/feedback-emails.model';
import { IFeedbackResponses } from 'app/shared/model/feedback-responses.model';

export interface IFeedbackRespondents {
  id?: number;
  category?: number;
  hasresponded?: boolean | null;
  respondedat?: string | null;
  createdat?: string;
  updatedat?: string;
  employee?: IEmployees | null;
  request?: IFeedbackRequests | null;
  feedbackemailsRespondents?: IFeedbackEmails[] | null;
  feedbackresponsesRespondents?: IFeedbackResponses[] | null;
}

export const defaultValue: Readonly<IFeedbackRespondents> = {
  hasresponded: false,
};
