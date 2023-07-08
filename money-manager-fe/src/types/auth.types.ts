interface ILoginBody {
  email: string;
  password: string;
}

interface IRegisterBody {
  fullName: string;
  email: string;
  password: string;
  rePassword: string;
}

export type { ILoginBody, IRegisterBody };
