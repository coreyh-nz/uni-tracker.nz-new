"use client";

import { Button } from "@/components/ui/button";
import { FieldError, FieldSet } from "@/components/ui/field";
import { Spinner } from "@/components/ui/spinner";
import { ROUTES } from "@/config/routes";
import { InvalidCredentialsError } from "@/features/auth/errors";
import { useLoginForm } from "@/features/auth/hooks/use-login-form";
import { useRouter } from "next/navigation";

export const LoginForm = () => {
    const router = useRouter();
    const { form, isPending, error } = useLoginForm({
        onSuccess: () => {
            router.push(ROUTES.Home);
        },
    });
    const credentialsInvalid = error instanceof InvalidCredentialsError;

    return (
        <form
            onSubmit={(e) => {
                e.preventDefault();
                void form.handleSubmit();
            }}
        >
            <FieldSet>
                <form.AppField name="email">
                    {(field) => (
                        <field.Input
                            label="Email"
                            invalid={credentialsInvalid}
                        />
                    )}
                </form.AppField>

                <form.AppField name="password">
                    {(field) => (
                        <field.Input
                            label="Password"
                            type="password"
                            invalid={credentialsInvalid}
                        />
                    )}
                </form.AppField>

                {error && <FieldError errors={[{ message: error.message }]} />}

                <Button disabled={isPending} type="submit">
                    {isPending && <Spinner />}
                    Login
                </Button>
            </FieldSet>
        </form>
    );
};
