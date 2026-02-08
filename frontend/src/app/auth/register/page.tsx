import { Button } from "@/components/ui/button";
import { ROUTES } from "@/config/routes";
import { RegisterForm } from "@/features/auth/components/register-form";
import Link from "next/link";

const RegisterPage = () => {
    return (
        <>
            <div className="space-y-2 text-center">
                <h1 className="text-2xl">Create Account</h1>

                <p className="text-muted-foreground">Join us and get started</p>
            </div>

            <RegisterForm />

            <div className="text-center text-sm leading-none text-muted-foreground">
                Already have an account?{" "}
                <Button
                    render={<Link href={ROUTES.Auth.Login} />}
                    nativeButton={false}
                    variant="link"
                    className="h-auto px-0"
                >
                    Sign in
                </Button>
            </div>
        </>
    );
};

export default RegisterPage;
