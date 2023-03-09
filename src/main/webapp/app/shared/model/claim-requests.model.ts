import dayjs from 'dayjs';
import { IEmployees } from 'app/shared/model/employees.model';
import { IClaimApprovers } from 'app/shared/model/claim-approvers.model';
import { IClaimDetails } from 'app/shared/model/claim-details.model';
import { IClaimImages } from 'app/shared/model/claim-images.model';

export interface IClaimRequests {
  id?: number;
  projectid?: number | null;
  comments?: string | null;
  amountreleased?: number | null;
  designation?: string | null;
  department?: string | null;
  location?: string | null;
  manager?: string | null;
  createdat?: string | null;
  updatedat?: string | null;
  employee?: IEmployees | null;
  claimapproversClaimrequests?: IClaimApprovers[] | null;
  claimdetailsClaimrequests?: IClaimDetails[] | null;
  claimimagesClaimrequests?: IClaimImages[] | null;
}

export const defaultValue: Readonly<IClaimRequests> = {};
