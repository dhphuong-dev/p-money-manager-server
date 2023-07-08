interface ILoginBody {
  email: string;
  password: string;
}

interface IRegisterBody {
  fullName: string;
  email: string;
  password: string;
  confirmPassword: string;
}

export type { ILoginBody, IRegisterBody };
