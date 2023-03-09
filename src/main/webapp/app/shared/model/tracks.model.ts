import dayjs from 'dayjs';
import { ICompetencies } from 'app/shared/model/competencies.model';
import { IInterviews } from 'app/shared/model/interviews.model';
import { IQuestions } from 'app/shared/model/questions.model';
import { IQuestionsFrequencyPerClientTrack } from 'app/shared/model/questions-frequency-per-client-track.model';
import { IQuestionsFrequencyPerTrack } from 'app/shared/model/questions-frequency-per-track.model';

export interface ITracks {
  id?: number;
  name?: string | null;
  description?: string | null;
  createdat?: string;
  updatedat?: string;
  deletedat?: string | null;
  competency?: ICompetencies | null;
  interviewsTracks?: IInterviews[] | null;
  questionsTracks?: IQuestions[] | null;
  questionsfrequencyperclienttrackTracks?: IQuestionsFrequencyPerClientTrack[] | null;
  questionsfrequencypertrackTracks?: IQuestionsFrequencyPerTrack[] | null;
}

export const defaultValue: Readonly<ITracks> = {};
