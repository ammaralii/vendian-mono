import dayjs from 'dayjs';
import { IAttributes } from 'app/shared/model/attributes.model';
import { IPcRatingAttributes } from 'app/shared/model/pc-rating-attributes.model';
import { IPcRaterAttributes } from 'app/shared/model/pc-rater-attributes.model';

export interface IRatingAttributeMappings {
  id?: number;
  effDate?: string;
  createdAt?: string;
  updatedAt?: string;
  endDate?: string | null;
  version?: number;
  attribute?: IAttributes;
  ratingAttribute?: IPcRatingAttributes;
  pcraterattributesRatingattributemappings?: IPcRaterAttributes[] | null;
}

export const defaultValue: Readonly<IRatingAttributeMappings> = {};
