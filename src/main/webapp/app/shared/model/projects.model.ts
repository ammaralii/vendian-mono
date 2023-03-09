import dayjs from 'dayjs';
import { IEmployees } from 'app/shared/model/employees.model';
import { IBusinessUnits } from 'app/shared/model/business-units.model';
import { IEmployeeProjects } from 'app/shared/model/employee-projects.model';
import { IInterviews } from 'app/shared/model/interviews.model';
import { IProjectCycles } from 'app/shared/model/project-cycles.model';
import { IProjectLeadership } from 'app/shared/model/project-leadership.model';
import { IQuestions } from 'app/shared/model/questions.model';
import { IQuestionsFrequencyPerClientTrack } from 'app/shared/model/questions-frequency-per-client-track.model';
import { IWorkLogDetails } from 'app/shared/model/work-log-details.model';
import { IZEmployeeProjects } from 'app/shared/model/z-employee-projects.model';

export interface IProjects {
  id?: number;
  name?: string | null;
  isactive?: boolean | null;
  isdelete?: boolean | null;
  startdate?: string | null;
  enddate?: string | null;
  colorcode?: string | null;
  createdat?: string;
  updatedat?: string;
  deliverymanagerid?: number | null;
  architectid?: number | null;
  isdeleted?: number | null;
  approvedresources?: number | null;
  releaseat?: string | null;
  projectmanager?: IEmployees | null;
  businessunit?: IBusinessUnits | null;
  employeeprojectsProjects?: IEmployeeProjects[] | null;
  interviewsProjects?: IInterviews[] | null;
  projectcyclesProjects?: IProjectCycles[] | null;
  projectleadershipProjects?: IProjectLeadership[] | null;
  questionsProjects?: IQuestions[] | null;
  questionsfrequencyperclienttrackProjects?: IQuestionsFrequencyPerClientTrack[] | null;
  worklogdetailsProjects?: IWorkLogDetails[] | null;
  zemployeeprojectsProjects?: IZEmployeeProjects[] | null;
}

export const defaultValue: Readonly<IProjects> = {
  isactive: false,
  isdelete: false,
};
