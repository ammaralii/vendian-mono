import dayjs from 'dayjs';

export interface IApproverFlows {
  id?: number;
  referenceType?: string;
  approverStatus?: string;
  approval?: string;
  currentStatus?: string;
  nextStatus?: string;
  effDate?: string;
  createdAt?: string;
  updatedAt?: string;
  version?: number;
}

export const defaultValue: Readonly<IApproverFlows> = {};
