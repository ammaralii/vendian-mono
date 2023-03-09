import dayjs from 'dayjs';
import { IClaimDetails } from 'app/shared/model/claim-details.model';

export interface IClaimTypes {
  id?: number;
  claimtype?: string | null;
  daterangerequired?: boolean | null;
  descriptionrequired?: boolean | null;
  parentid?: number | null;
  createdat?: string | null;
  updatedat?: string | null;
  claimdetailsClaimtypes?: IClaimDetails[] | null;
}

export const defaultValue: Readonly<IClaimTypes> = {
  daterangerequired: false,
  descriptionrequired: false,
};
