import { registerSchema } from "@/features/auth/schemas/register.schema";
import { register } from "@/features/auth/services/auth.service";
import { RegisterRequest, RegisterResponse } from "@/features/auth/types";
import { useMutationForm } from "@/hooks/use-mutation-form";
import { ApiError } from "@/lib/errors";
import { useMutation } from "@tanstack/react-query";

type UseRegisterFormProps = {
    onSuccess?: () => void;
};

export const useRegisterForm = ({ onSuccess }: UseRegisterFormProps) => {
    const { mutateAsync, isPending } = useMutation<
        RegisterResponse,
        ApiError,
        RegisterRequest
    >({
        mutationFn: register,
    });

    const { form, globalError } = useMutationForm({
        defaultValues: {
            firstName: "",
            lastName: "",
            email: "",
            password: "",
        },
        schema: registerSchema,
        mutateAsync,
        onSuccess,
    });

    return { form, isPending, error: globalError };
};
