export type LoginRequest = {
    email: string;
    password: string;
};

export type LoginResponse = unknown;

export type RegisterRequest = {
    firstName: string;
    lastName: string;
    email: string;
    password: string;
};

export type RegisterResponse = unknown;
