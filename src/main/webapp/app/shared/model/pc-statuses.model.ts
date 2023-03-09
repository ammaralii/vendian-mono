import dayjs from 'dayjs';
import { IPcRatings } from 'app/shared/model/pc-ratings.model';

export interface IPcStatuses {
  id?: number;
  name?: string;
  group?: string;
  systemKey?: string | null;
  createdAt?: string;
  updatedAt?: string;
  deletedAt?: string | null;
  version?: number;
  pcratingsStatuses?: IPcRatings[] | null;
}

export const defaultValue: Readonly<IPcStatuses> = {};
