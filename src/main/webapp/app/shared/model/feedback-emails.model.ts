import dayjs from 'dayjs';
import { IFeedbackRespondents } from 'app/shared/model/feedback-respondents.model';

export interface IFeedbackEmails {
  id?: number;
  to?: string | null;
  body?: string | null;
  status?: number | null;
  sentat?: string | null;
  createdat?: string;
  updatedat?: string;
  respondent?: IFeedbackRespondents | null;
}

export const defaultValue: Readonly<IFeedbackEmails> = {};
