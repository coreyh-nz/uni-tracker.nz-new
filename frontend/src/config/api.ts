const API_BASE = process.env.NEXT_PUBLIC_API_BASE?.replace(/\/+$/, "") ?? "";

export const API = {
    V1: {
        Auth: {
            LOGIN: `${API_BASE}/api/auth/login`,
            REGISTER: `${API_BASE}/api/auth/register`,
        },
    },
};
