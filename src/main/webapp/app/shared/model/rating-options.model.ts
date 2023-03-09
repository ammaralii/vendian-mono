import dayjs from 'dayjs';
import { IPcRaterAttributes } from 'app/shared/model/pc-rater-attributes.model';

export interface IRatingOptions {
  id?: number;
  name?: string;
  description?: string | null;
  from?: number;
  to?: number | null;
  effDate?: string;
  createdAt?: string;
  updatedAt?: string;
  endDate?: string | null;
  version?: number;
  pcraterattributesRatingoptions?: IPcRaterAttributes[] | null;
}

export const defaultValue: Readonly<IRatingOptions> = {};
