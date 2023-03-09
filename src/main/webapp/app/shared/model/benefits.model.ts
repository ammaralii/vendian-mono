import dayjs from 'dayjs';
import { ICompensationBenefits } from 'app/shared/model/compensation-benefits.model';

export interface IBenefits {
  id?: number;
  name?: string;
  isdeleted?: boolean | null;
  createdat?: string | null;
  updatedat?: string | null;
  compensationbenefitsBenefits?: ICompensationBenefits[] | null;
}

export const defaultValue: Readonly<IBenefits> = {
  isdeleted: false,
};
