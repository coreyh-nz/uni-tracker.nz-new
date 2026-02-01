import { Button } from "@/components/ui/button";
import { ROUTES } from "@/config/routes";
import { LoginForm } from "@/features/auth/components/login-form";
import Link from "next/link";

const LoginPage = () => {
    return (
        <>
            <div className="space-y-2 text-center">
                <h1 className="text-2xl">Welcome Back</h1>

                <p className="text-muted-foreground">
                    Sign in to your account to continue
                </p>
            </div>

            <LoginForm />

            <div className="text-center text-sm leading-none text-muted-foreground">
                Don&#39;t have an account?{" "}
                <Button
                    render={<Link href={ROUTES.Auth.Register} />}
                    nativeButton={false}
                    variant="link"
                    className="h-auto px-0"
                >
                    Sign up
                </Button>
            </div>
        </>
    );
};

export default LoginPage;
