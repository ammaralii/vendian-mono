import { IZEmployeeProjects } from 'app/shared/model/z-employee-projects.model';

export interface IEvents {
  id?: number;
  type?: string | null;
  zemployeeprojectsEvents?: IZEmployeeProjects[] | null;
}

export const defaultValue: Readonly<IEvents> = {};
