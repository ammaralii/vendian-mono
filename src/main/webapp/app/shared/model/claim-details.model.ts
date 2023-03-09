import dayjs from 'dayjs';
import { IClaimRequests } from 'app/shared/model/claim-requests.model';
import { IClaimTypes } from 'app/shared/model/claim-types.model';

export interface IClaimDetails {
  id?: number;
  amount?: number | null;
  startdate?: string | null;
  enddate?: string | null;
  description?: string | null;
  createdat?: string | null;
  updatedat?: string | null;
  claimrequest?: IClaimRequests | null;
  claimtype?: IClaimTypes | null;
}

export const defaultValue: Readonly<IClaimDetails> = {};
