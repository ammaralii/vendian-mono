import dayjs from 'dayjs';
import { IDocuments } from 'app/shared/model/documents.model';
import { IDesignations } from 'app/shared/model/designations.model';
import { IEmployeeJobInfo } from 'app/shared/model/employee-job-info.model';

export interface IDesignationJobDescriptions {
  id?: number;
  createdat?: string | null;
  updatedat?: string | null;
  document?: IDocuments;
  designation?: IDesignations;
  employeejobinfoJobdescriptions?: IEmployeeJobInfo[] | null;
}

export const defaultValue: Readonly<IDesignationJobDescriptions> = {};
