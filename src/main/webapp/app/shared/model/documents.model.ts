import dayjs from 'dayjs';
import { IDesignationJobDescriptions } from 'app/shared/model/designation-job-descriptions.model';
import { IEmployeeComments } from 'app/shared/model/employee-comments.model';
import { IEmployeeDocuments } from 'app/shared/model/employee-documents.model';

export interface IDocuments {
  id?: number;
  nameContentType?: string;
  name?: string;
  urlContentType?: string;
  url?: string;
  createdat?: string | null;
  updatedat?: string | null;
  designationjobdescriptionsDocuments?: IDesignationJobDescriptions[] | null;
  employeecommentsDocuments?: IEmployeeComments[] | null;
  employeedocumentsDocuments?: IEmployeeDocuments[] | null;
}

export const defaultValue: Readonly<IDocuments> = {};
