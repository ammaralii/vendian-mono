import dayjs from 'dayjs';
import { IDesignationJobDescriptions } from 'app/shared/model/designation-job-descriptions.model';
import { IEmployeeJobInfo } from 'app/shared/model/employee-job-info.model';
import { IEmployees } from 'app/shared/model/employees.model';
import { IUserRatingsRemarks } from 'app/shared/model/user-ratings-remarks.model';

export interface IDesignations {
  id?: number;
  name?: string;
  createdat?: string | null;
  updatedat?: string | null;
  deletedat?: string | null;
  designationjobdescriptionsDesignations?: IDesignationJobDescriptions[] | null;
  employeejobinfoDesignations?: IEmployeeJobInfo[] | null;
  employeesDesignations?: IEmployees[] | null;
  userratingsremarksDesignationafterpromotions?: IUserRatingsRemarks[] | null;
  userratingsremarksDesignationafterredesignations?: IUserRatingsRemarks[] | null;
}

export const defaultValue: Readonly<IDesignations> = {};
