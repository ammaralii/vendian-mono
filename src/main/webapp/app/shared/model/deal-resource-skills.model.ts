import dayjs from 'dayjs';
import { IDealResources } from 'app/shared/model/deal-resources.model';
import { ISkills } from 'app/shared/model/skills.model';

export interface IDealResourceSkills {
  id?: number;
  createdat?: string | null;
  updatedat?: string | null;
  deletedat?: string | null;
  resource?: IDealResources | null;
  skill?: ISkills | null;
}

export const defaultValue: Readonly<IDealResourceSkills> = {};
