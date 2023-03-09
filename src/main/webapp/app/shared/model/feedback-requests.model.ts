import dayjs from 'dayjs';
import { IEmployees } from 'app/shared/model/employees.model';
import { IFeedbackRespondents } from 'app/shared/model/feedback-respondents.model';

export interface IFeedbackRequests {
  id?: number;
  status?: number | null;
  isreportavailable?: boolean | null;
  reportpath?: string | null;
  approvedat?: string | null;
  expiredat?: string | null;
  createdat?: string;
  updatedat?: string;
  employee?: IEmployees | null;
  linemanager?: IEmployees | null;
  feedbackrespondentsRequests?: IFeedbackRespondents[] | null;
}

export const defaultValue: Readonly<IFeedbackRequests> = {
  isreportavailable: false,
};
