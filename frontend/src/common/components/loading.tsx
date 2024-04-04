import { Loader } from "lucide-react";

export default function Loading () {
  return (
    <div className='w-screen h-[50vh] grid place-items-center'>
      <Loader className="animate-spin" />
    </div>
  )
}
