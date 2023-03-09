import dayjs from 'dayjs';
import { IClaimApprovers } from 'app/shared/model/claim-approvers.model';

export interface IClaimStatus {
  id?: number;
  status?: string | null;
  createdat?: string | null;
  updatedat?: string | null;
  claimapproversStatuses?: IClaimApprovers[] | null;
}

export const defaultValue: Readonly<IClaimStatus> = {};
