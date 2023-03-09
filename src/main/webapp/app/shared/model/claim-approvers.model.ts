import dayjs from 'dayjs';
import { IClaimStatus } from 'app/shared/model/claim-status.model';
import { IClaimRequests } from 'app/shared/model/claim-requests.model';

export interface IClaimApprovers {
  id?: number;
  referenceid?: number | null;
  designation?: string | null;
  approveorder?: number | null;
  reference?: string | null;
  comments?: string | null;
  approvedby?: string | null;
  createdat?: string | null;
  updatedat?: string | null;
  status?: IClaimStatus | null;
  claimrequest?: IClaimRequests | null;
}

export const defaultValue: Readonly<IClaimApprovers> = {};
