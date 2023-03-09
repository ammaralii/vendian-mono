import dayjs from 'dayjs';
import { IEmployees } from 'app/shared/model/employees.model';
import { IDealRequirementMatchingResources } from 'app/shared/model/deal-requirement-matching-resources.model';
import { IDealResourceSkills } from 'app/shared/model/deal-resource-skills.model';

export interface IDealResources {
  id?: number;
  firstname?: string | null;
  lastname?: string | null;
  joiningdate?: string | null;
  externalexpyears?: number | null;
  externalexpmonths?: number | null;
  createdat?: string | null;
  updatedat?: string | null;
  type?: string;
  isactive?: boolean;
  employee?: IEmployees | null;
  dealrequirementmatchingresourcesResources?: IDealRequirementMatchingResources[] | null;
  dealresourceskillsResources?: IDealResourceSkills[] | null;
}

export const defaultValue: Readonly<IDealResources> = {
  isactive: false,
};
