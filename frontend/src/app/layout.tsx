import QueryProvider from "@/providers/query-provider";
import type { Metadata } from "next";
import { Geist, Geist_Mono, Outfit } from "next/font/google";
import "./globals.css";

const outfit = Outfit({ subsets: ["latin"], variable: "--font-sans" });

const geistSans = Geist({
    variable: "--font-geist-sans",
    subsets: ["latin"],
});

const geistMono = Geist_Mono({
    variable: "--font-geist-mono",
    subsets: ["latin"],
});

export default function RootLayout({
    children,
}: Readonly<{
    children: React.ReactNode;
}>) {
    return (
        <html lang="en" className={outfit.variable}>
            <body
                className={`${geistSans.variable} ${geistMono.variable} flex min-h-screen antialiased`}
            >
                <QueryProvider>{children}</QueryProvider>
            </body>
        </html>
    );
}

export const metadata: Metadata = {
    title: "Uni-Tracker",
};
