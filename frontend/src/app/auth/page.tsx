import { ROUTES } from "@/config/routes";
import { permanentRedirect } from "next/navigation";

const AuthPage = async () => {
    permanentRedirect(ROUTES.Auth.Login);
};

export default AuthPage;
