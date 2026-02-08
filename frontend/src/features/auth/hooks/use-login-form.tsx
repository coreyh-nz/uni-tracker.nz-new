import { loginSchema } from "@/features/auth/schemas/login.schema";
import { login } from "@/features/auth/services/auth.service";
import { LoginRequest, LoginResponse } from "@/features/auth/types";
import { useMutationForm } from "@/hooks/use-mutation-form";
import { ApiError } from "@/lib/errors";
import { useMutation } from "@tanstack/react-query";

type UseLoginFormProps = {
    onSuccess?: () => void;
};

export const useLoginForm = ({ onSuccess }: UseLoginFormProps) => {
    const { mutateAsync, isPending } = useMutation<
        LoginResponse,
        ApiError,
        LoginRequest
    >({
        mutationFn: login,
    });

    const { form, globalError } = useMutationForm({
        defaultValues: { email: "", password: "" },
        schema: loginSchema,
        mutateAsync,
        onSuccess,
    });

    return { form, isPending, error: globalError };
};
