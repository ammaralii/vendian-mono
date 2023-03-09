import dayjs from 'dayjs';
import { IClaimRequests } from 'app/shared/model/claim-requests.model';

export interface IClaimImages {
  id?: number;
  images?: string | null;
  createdat?: string | null;
  updatedat?: string | null;
  claimrequest?: IClaimRequests | null;
}

export const defaultValue: Readonly<IClaimImages> = {};
