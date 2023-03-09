import dayjs from 'dayjs';
import { IDocuments } from 'app/shared/model/documents.model';
import { IEmployees } from 'app/shared/model/employees.model';

export interface IEmployeeComments {
  id?: number;
  titleContentType?: string;
  title?: string;
  contentContentType?: string;
  content?: string;
  datedContentType?: string;
  dated?: string;
  createdat?: string | null;
  updatedat?: string | null;
  document?: IDocuments | null;
  commenter?: IEmployees;
  employee?: IEmployees;
}

export const defaultValue: Readonly<IEmployeeComments> = {};
