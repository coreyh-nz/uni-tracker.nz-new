import {
    Field,
    FieldContent,
    FieldDescription,
    FieldError,
    FieldLabel,
} from "@/components/ui/field";
import { Input } from "@/components/ui/input";
import { createFormHook, createFormHookContexts } from "@tanstack/react-form";
import { InputHTMLAttributes, ReactNode } from "react";

const { fieldContext, formContext, useFieldContext } = createFormHookContexts();

export const { useAppForm } = createFormHook({
    fieldComponents: {
        Input: FormInput,
    },
    formComponents: {},
    fieldContext,
    formContext,
});

type FormControlProps = {
    label: string;
    description?: string;
    invalid?: boolean;
};

type FormBaseProps = FormControlProps & {
    children: ReactNode;
    horizontal?: boolean;
    controlFirst?: boolean;
    invalid?: boolean;
};

function FormBase({
    children,
    label,
    description,
    controlFirst,
    horizontal,
    invalid,
}: FormBaseProps) {
    const field = useFieldContext();
    const isInvalid =
        invalid || (field.state.meta.isTouched && !field.state.meta.isValid);
    const labelElement = (
        <>
            <FieldLabel htmlFor={field.name}>{label}</FieldLabel>
            {description && <FieldDescription>{description}</FieldDescription>}
        </>
    );
    const errorElem = isInvalid && (
        <FieldError errors={field.state.meta.errors} />
    );

    return (
        <Field
            data-invalid={isInvalid}
            orientation={horizontal ? "horizontal" : undefined}
        >
            {controlFirst ? (
                <>
                    {children}
                    <FieldContent>
                        {labelElement}
                        {errorElem}
                    </FieldContent>
                </>
            ) : (
                <>
                    <FieldContent>{labelElement}</FieldContent>
                    {children}
                    {errorElem}
                </>
            )}
        </Field>
    );
}

type FormInputProps = FormControlProps & {
    type?: InputHTMLAttributes<HTMLInputElement>["type"];
};

export function FormInput({
    type = "text",
    invalid = false,
    ...props
}: FormInputProps) {
    const field = useFieldContext<string>();
    const isInvalid =
        invalid || (field.state.meta.isTouched && !field.state.meta.isValid);

    return (
        <FormBase {...props}>
            <Input
                id={field.name}
                name={field.name}
                type={type}
                value={field.state.value}
                onBlur={field.handleBlur}
                onChange={(e) => field.handleChange(e.target.value)}
                aria-invalid={isInvalid}
            />
        </FormBase>
    );
}
