import dayjs from 'dayjs';
import { IDocuments } from 'app/shared/model/documents.model';
import { IEmployees } from 'app/shared/model/employees.model';

export interface IEmployeeDocuments {
  id?: number;
  createdat?: string | null;
  updatedat?: string | null;
  document?: IDocuments;
  employee?: IEmployees;
}

export const defaultValue: Readonly<IEmployeeDocuments> = {};
