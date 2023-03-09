import dayjs from 'dayjs';
import { IRaterAttributes } from 'app/shared/model/rater-attributes.model';
import { IAttributes } from 'app/shared/model/attributes.model';
import { IRatingAttributes } from 'app/shared/model/rating-attributes.model';

export interface IRaterAttributeMappings {
  id?: number;
  effdate?: string | null;
  enddate?: string | null;
  createdat?: string;
  updatedat?: string;
  raterattribute?: IRaterAttributes | null;
  attributesFor?: IAttributes | null;
  attributesBy?: IAttributes | null;
  ratingattributesRaterattributemappings?: IRatingAttributes[] | null;
}

export const defaultValue: Readonly<IRaterAttributeMappings> = {};
