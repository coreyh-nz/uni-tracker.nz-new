import { Logo } from "@/components/logo";
import { Card, CardContent } from "@/components/ui/card";
import { PropsWithChildren } from "react";

const AuthLayout = ({ children }: PropsWithChildren) => {
    return (
        <div className="flex grow items-center justify-center bg-background">
            <Card className="flex min-h-128 w-full max-w-4xl flex-col p-0 lg:flex-row">
                <div className="flex items-center justify-center bg-linear-to-br from-primary to-[color-mix(in_srgb,var(--primary),black_25%)] p-8 lg:w-1/2 lg:flex-1">
                    <Logo variant="long" className="block max-w-lg lg:hidden" />
                    <Logo
                        variant="short"
                        className="hidden max-w-xs lg:block"
                    />
                </div>
                <CardContent className="flex items-center justify-center p-8 lg:flex-1">
                    <div className="flex w-full max-w-md flex-col space-y-6">
                        {children}
                    </div>
                </CardContent>
            </Card>
        </div>
    );
};

export default AuthLayout;
