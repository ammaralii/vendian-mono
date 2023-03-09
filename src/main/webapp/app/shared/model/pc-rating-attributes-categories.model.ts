import dayjs from 'dayjs';
import { IRatingCategories } from 'app/shared/model/rating-categories.model';
import { IPcRatingAttributes } from 'app/shared/model/pc-rating-attributes.model';

export interface IPcRatingAttributesCategories {
  id?: number;
  effDate?: string;
  createdAt?: string;
  updatedAt?: string;
  endDate?: string | null;
  version?: number;
  category?: IRatingCategories;
  ratingAttribute?: IPcRatingAttributes;
}

export const defaultValue: Readonly<IPcRatingAttributesCategories> = {};
