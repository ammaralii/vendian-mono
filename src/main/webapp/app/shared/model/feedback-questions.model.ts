import dayjs from 'dayjs';
import { IEmployees } from 'app/shared/model/employees.model';
import { IFeedbackResponses } from 'app/shared/model/feedback-responses.model';

export interface IFeedbackQuestions {
  id?: number;
  question?: string | null;
  isdefault?: boolean | null;
  area?: string | null;
  competency?: string | null;
  category?: number | null;
  isskill?: boolean | null;
  skilltype?: number | null;
  createdat?: string;
  updatedat?: string;
  employee?: IEmployees | null;
  feedbackresponsesQuestions?: IFeedbackResponses[] | null;
}

export const defaultValue: Readonly<IFeedbackQuestions> = {
  isdefault: false,
  isskill: false,
};
