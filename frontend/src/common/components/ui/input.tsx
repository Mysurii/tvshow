import * as React from "react"

import { cn } from "@/lib/utils"

export interface InputProps
  extends React.InputHTMLAttributes<HTMLInputElement> {
  icon?: React.ReactNode
}

const Input = React.forwardRef<HTMLInputElement, InputProps>(
  ( { className, type, icon, ...props }, ref ) => {
    return (
      <div className="relative flex items-center w-full max-w-xl">
        <input
          type={type}
          className={cn(
            "flex h-10 w-full rounded-lg border border-emerald-600 bg-background px-3 py-2 pr-12 text-sm  file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none disabled:cursor-not-allowed disabled:opacity-50",
            className
          )}
          ref={ref}
          {...props}
        />
        {icon && <div className="absolute right-0 mr-2">{icon}</div>}
      </div>
    )
  }
)
Input.displayName = "Input"

export { Input }
