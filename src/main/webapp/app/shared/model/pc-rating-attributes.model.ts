import dayjs from 'dayjs';
import { IPcRatingAttributesCategories } from 'app/shared/model/pc-rating-attributes-categories.model';
import { IRatingAttributeMappings } from 'app/shared/model/rating-attribute-mappings.model';

export interface IPcRatingAttributes {
  id?: number;
  name?: string;
  effDate?: string;
  createdAt?: string;
  updatedAt?: string;
  endDate?: string | null;
  version?: number;
  subCategory?: string | null;
  description?: string | null;
  pcratingattributescategoriesRatingattributes?: IPcRatingAttributesCategories[] | null;
  ratingattributemappingsRatingattributes?: IRatingAttributeMappings[] | null;
}

export const defaultValue: Readonly<IPcRatingAttributes> = {};
