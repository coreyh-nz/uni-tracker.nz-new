import { useAppForm } from "@/components/form";
import { ApiError, UnexpectedError } from "@/lib/errors";
import { useState } from "react";

type UseMutationFormOptions<TValues> = {
    defaultValues: TValues;
    schema: any; // eslint-disable-line @typescript-eslint/no-explicit-any
    mutateAsync: (values: TValues) => Promise<unknown>;
    onSuccess?: () => void;
};

export const useMutationForm = <TValues,>({
    defaultValues,
    schema,
    mutateAsync,
    onSuccess,
}: UseMutationFormOptions<TValues>) => {
    const [globalError, setGlobalError] = useState<ApiError | null>(null);

    const form = useAppForm({
        defaultValues,
        validators: { onSubmit: schema },
        onSubmit: async (args) => {
            const value: TValues = args.value;
            setGlobalError(null);
            try {
                await mutateAsync(value);
                onSuccess?.();
            } catch (error) {
                setGlobalError(
                    error instanceof ApiError ? error : new UnexpectedError(),
                );
            }
        },
    });

    return { form, globalError, setGlobalError };
};
