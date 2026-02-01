export class ApiError extends Error {
    override readonly message: string;

    constructor(message: string) {
        super();
        this.message = message;
        Object.setPrototypeOf(this, new.target.prototype);
    }
}

export class UnexpectedError extends ApiError {
    constructor() {
        super("Unexpected error");
    }
}
