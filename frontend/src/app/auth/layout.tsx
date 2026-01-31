import { Card, CardContent } from "@/components/ui/card";
import { PropsWithChildren } from "react";

const AuthLayout = ({ children }: PropsWithChildren) => {
    return (
        <div className="bg-background flex min-h-screen items-center justify-center">
            <Card className="w-full max-w-5xl">
                <CardContent>{children}</CardContent>
            </Card>
        </div>
    );
};

export default AuthLayout;
