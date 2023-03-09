import dayjs from 'dayjs';
import { IPcRatings } from 'app/shared/model/pc-ratings.model';
import { IUserGoals } from 'app/shared/model/user-goals.model';

export interface IUserGoalRaterAttributes {
  id?: number;
  ugraRatingContentType?: string | null;
  ugraRating?: string | null;
  commentContentType?: string | null;
  comment?: string | null;
  createdAt?: string;
  updatedAt?: string;
  deletedAt?: string | null;
  version?: number;
  rating?: IPcRatings;
  usergoal?: IUserGoals | null;
}

export const defaultValue: Readonly<IUserGoalRaterAttributes> = {};
