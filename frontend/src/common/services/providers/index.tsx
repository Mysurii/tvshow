import { ReactNode } from "react";
import { ThemeProvider } from "./theme-provider";
import TanstackQueryProvider from "./tanstack-query-provider";

type Props = {
  children: ReactNode
}

export function ProviderWrapper ( { children }: Props ) {
  return (
    <TanstackQueryProvider>
      <ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
        {children}
      </ThemeProvider>
    </TanstackQueryProvider>
  )
}