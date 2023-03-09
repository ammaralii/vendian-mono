import dayjs from 'dayjs';
import { IRatingAttributeMappings } from 'app/shared/model/rating-attribute-mappings.model';
import { IRatingOptions } from 'app/shared/model/rating-options.model';
import { IPcRatings } from 'app/shared/model/pc-ratings.model';

export interface IPcRaterAttributes {
  id?: number;
  pcRatingContentType?: string | null;
  pcRating?: string | null;
  commentContentType?: string | null;
  comment?: string | null;
  createdAt?: string;
  updatedAt?: string;
  deletedAt?: string | null;
  version?: number;
  ratingAttributeMapping?: IRatingAttributeMappings;
  ratingOption?: IRatingOptions | null;
  rating?: IPcRatings;
}

export const defaultValue: Readonly<IPcRaterAttributes> = {};
