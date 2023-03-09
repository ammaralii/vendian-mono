import dayjs from 'dayjs';
import { IRatings } from 'app/shared/model/ratings.model';
import { IRaterAttributeMappings } from 'app/shared/model/rater-attribute-mappings.model';

export interface IRatingAttributes {
  id?: number;
  raRatingContentType?: string | null;
  raRating?: string | null;
  commentContentType?: string | null;
  comment?: string | null;
  createdat?: string;
  updatedat?: string;
  rating?: IRatings | null;
  raterattributemapping?: IRaterAttributeMappings | null;
}

export const defaultValue: Readonly<IRatingAttributes> = {};
