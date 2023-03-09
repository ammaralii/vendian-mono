import dayjs from 'dayjs';
import { IEmployees } from 'app/shared/model/employees.model';
import { IPcRatings } from 'app/shared/model/pc-ratings.model';

export interface IPcRatingsTrainings {
  id?: number;
  strength?: string | null;
  developmentArea?: string | null;
  careerAmbition?: string | null;
  shortCourse?: string | null;
  technicalTraining?: string | null;
  softSkillsTraining?: string | null;
  criticalPosition?: boolean | null;
  highPotential?: boolean | null;
  createdAt?: string;
  updatedAt?: string;
  deletedAt?: string | null;
  version?: number;
  successorFor?: IEmployees | null;
  rating?: IPcRatings;
}

export const defaultValue: Readonly<IPcRatingsTrainings> = {
  criticalPosition: false,
  highPotential: false,
};
