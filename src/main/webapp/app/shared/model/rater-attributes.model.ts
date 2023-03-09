import dayjs from 'dayjs';
import { IRaterAttributeMappings } from 'app/shared/model/rater-attribute-mappings.model';

export interface IRaterAttributes {
  id?: number;
  name?: string | null;
  title?: string | null;
  description?: string | null;
  effdate?: string | null;
  enddate?: string | null;
  createdat?: string;
  updatedat?: string;
  raterattributemappingsRaterattributes?: IRaterAttributeMappings[] | null;
}

export const defaultValue: Readonly<IRaterAttributes> = {};
