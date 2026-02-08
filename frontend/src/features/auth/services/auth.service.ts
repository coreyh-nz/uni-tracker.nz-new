import { API } from "@/config/api";
import { InvalidCredentialsError } from "@/features/auth/errors";
import {
    LoginRequest,
    LoginResponse,
    RegisterRequest,
    RegisterResponse,
} from "@/features/auth/types";
import { UnexpectedError } from "@/lib/errors";

export const login = async ({
    email,
    password,
}: LoginRequest): Promise<LoginResponse> => {
    const res = await fetch(API.V1.Auth.LOGIN, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
    });

    if (!res.ok) {
        if (res.status === 401) {
            throw new InvalidCredentialsError();
        }
        throw new UnexpectedError();
    }

    return res.json();
};

export const register = async (
    request: RegisterRequest,
): Promise<RegisterResponse> => {
    const res = await fetch(API.V1.Auth.REGISTER, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(request),
    });

    if (!res.ok) {
        throw new UnexpectedError();
    }

    return res.json();
};
