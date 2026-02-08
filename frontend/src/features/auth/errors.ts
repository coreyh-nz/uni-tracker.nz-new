import { ApiError } from "@/lib/errors";

export class InvalidCredentialsError extends ApiError {
    constructor() {
        super("Incorrect credentials");
    }
}
