"use client";

import { Button } from "@/components/ui/button";
import { FieldSet } from "@/components/ui/field";
import { Spinner } from "@/components/ui/spinner";
import { ROUTES } from "@/config/routes";
import { useRegisterForm } from "@/features/auth/hooks/use-register-form";
import { useRouter } from "next/navigation";

export const RegisterForm = () => {
    const router = useRouter();
    const { form, isPending } = useRegisterForm({
        onSuccess: () => {
            // todo - eventually redirect to confirm email when backend is implemented
            router.push(ROUTES.Auth.Login);
        },
    });

    return (
        <form
            onSubmit={(e) => {
                e.preventDefault();
                void form.handleSubmit();
            }}
        >
            <FieldSet>
                <FieldSet className="sm:flex-row">
                    <form.AppField name="firstName">
                        {(field) => <field.Input label="First Name" />}
                    </form.AppField>

                    <form.AppField name="lastName">
                        {(field) => <field.Input label="Last Name" />}
                    </form.AppField>
                </FieldSet>

                <form.AppField name="email">
                    {(field) => <field.Input label="Email" />}
                </form.AppField>

                <form.AppField name="password">
                    {(field) => (
                        <field.Input label="Password" type="password" />
                    )}
                </form.AppField>

                <Button disabled={isPending} type="submit">
                    {isPending && <Spinner />}
                    Register
                </Button>
            </FieldSet>
        </form>
    );
};
