interface ILoginBody {
  email: string;
  password: string;
}

interface IRegisterBody {
  email: string;
  password: string;
  fullName: string;
}

export type { ILoginBody, IRegisterBody };
