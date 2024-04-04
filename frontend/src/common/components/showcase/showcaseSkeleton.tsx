import { Skeleton } from "@/common/components/ui/skeleton";

export default function ShowCaseSkeleton () {
  return (
    <div className="p-0 pb-2 text-center rounded h-[300px] w-full">
      <Skeleton className=" h-full" />
      <Skeleton className="mt-4 h-8" />
    </div>
  )
}
