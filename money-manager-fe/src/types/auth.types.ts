interface ILoginBody {
  username: string;
  password: string;
}

interface IRegisterBody {
  username: string;
  password: string;
  fullName: string;
}

export type { ILoginBody, IRegisterBody };
