import dayjs from 'dayjs';

export interface IQuestionsProcessingEventLogs {
  id?: number;
  processedOn?: string;
  createdat?: string;
  updatedat?: string;
}

export const defaultValue: Readonly<IQuestionsProcessingEventLogs> = {};
