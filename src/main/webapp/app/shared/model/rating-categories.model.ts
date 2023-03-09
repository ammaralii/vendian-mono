import dayjs from 'dayjs';
import { IPcRatingAttributesCategories } from 'app/shared/model/pc-rating-attributes-categories.model';

export interface IRatingCategories {
  id?: number;
  name?: string;
  effDate?: string;
  createdAt?: string;
  updatedAt?: string;
  endDate?: string | null;
  version?: number;
  pcratingattributescategoriesCategories?: IPcRatingAttributesCategories[] | null;
}

export const defaultValue: Readonly<IRatingCategories> = {};
