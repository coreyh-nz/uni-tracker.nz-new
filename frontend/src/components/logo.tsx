import { cn } from "@/lib/utils";
import Image, { ImageProps } from "next/image";

type LogoProps = {
    variant?: "short" | "long";
} & Omit<ImageProps, "src" | "alt">;

export const Logo = ({ variant = "short", className, ...props }: LogoProps) => {
    const { src, width, height } =
        variant === "long"
            ? { src: "/logo-long.svg", width: 1889, height: 1687 }
            : { src: "/logo.svg", width: 5015, height: 1386 };
    return (
        <Image
            src={src}
            alt="Uni-Tracker Logo"
            height={height}
            width={width}
            className={cn("h-auto w-full drop-shadow filter", className)}
            {...props}
        />
    );
};
