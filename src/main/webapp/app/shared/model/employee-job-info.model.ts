import dayjs from 'dayjs';
import { IEmployees } from 'app/shared/model/employees.model';
import { IDesignations } from 'app/shared/model/designations.model';
import { IDepartments } from 'app/shared/model/departments.model';
import { IEmploymentTypes } from 'app/shared/model/employment-types.model';
import { IDesignationJobDescriptions } from 'app/shared/model/designation-job-descriptions.model';
import { IDivisions } from 'app/shared/model/divisions.model';
import { IBusinessUnits } from 'app/shared/model/business-units.model';
import { ICompetencies } from 'app/shared/model/competencies.model';

export interface IEmployeeJobInfo {
  id?: number;
  title?: string;
  grade?: string;
  subgrade?: string;
  startdate?: string;
  enddate?: string | null;
  createdat?: string | null;
  updatedat?: string | null;
  location?: number | null;
  grosssalaryContentType?: string | null;
  grosssalary?: string | null;
  fuelallowanceContentType?: string | null;
  fuelallowance?: string | null;
  employee?: IEmployees;
  designation?: IDesignations;
  reviewmanager?: IEmployees | null;
  manager?: IEmployees | null;
  department?: IDepartments;
  employmenttype?: IEmploymentTypes | null;
  jobdescription?: IDesignationJobDescriptions | null;
  division?: IDivisions | null;
  businessunit?: IBusinessUnits | null;
  competency?: ICompetencies | null;
}

export const defaultValue: Readonly<IEmployeeJobInfo> = {};
