import { IUser } from 'app/entities/user/user.model';

export interface IProject {
  id?: number;
  name?: string;
  owner?: IUser | null;
}

export class Project implements IProject {
  constructor(public id?: number, public name?: string, public owner?: IUser | null) {}
}

export function getProjectIdentifier(project: IProject): number | undefined {
  return project.id;
}
