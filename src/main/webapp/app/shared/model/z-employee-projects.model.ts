import dayjs from 'dayjs';
import { IEvents } from 'app/shared/model/events.model';
import { IEmployees } from 'app/shared/model/employees.model';
import { IProjects } from 'app/shared/model/projects.model';
import { IEmployeeProjects } from 'app/shared/model/employee-projects.model';

export interface IZEmployeeProjects {
  id?: number;
  status?: boolean | null;
  type?: string | null;
  startdate?: string | null;
  enddate?: string | null;
  name?: string | null;
  allocation?: boolean | null;
  billed?: string | null;
  createdat?: string;
  updatedat?: string;
  deliverymanagerid?: number | null;
  architectid?: number | null;
  notes?: string | null;
  previousenddate?: string | null;
  data?: string | null;
  extendedenddate?: string | null;
  probability?: string | null;
  event?: IEvents | null;
  employee?: IEmployees | null;
  project?: IProjects | null;
  employeeproject?: IEmployeeProjects | null;
  assignor?: IEmployees | null;
  projectmanager?: IEmployees | null;
}

export const defaultValue: Readonly<IZEmployeeProjects> = {
  status: false,
  allocation: false,
};
