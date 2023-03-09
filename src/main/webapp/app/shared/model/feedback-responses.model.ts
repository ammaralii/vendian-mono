import dayjs from 'dayjs';
import { IFeedbackRespondents } from 'app/shared/model/feedback-respondents.model';
import { IFeedbackQuestions } from 'app/shared/model/feedback-questions.model';

export interface IFeedbackResponses {
  id?: number;
  answerContentType?: string | null;
  answer?: string | null;
  ratingContentType?: string | null;
  rating?: string | null;
  createdat?: string;
  updatedat?: string;
  respondent?: IFeedbackRespondents | null;
  question?: IFeedbackQuestions | null;
}

export const defaultValue: Readonly<IFeedbackResponses> = {};
