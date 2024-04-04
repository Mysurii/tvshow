import { Skeleton } from '@/common/components/ui/skeleton'

export default function GenreSkeleton () {
  return (
    <ul className="flex gap-2 flex-wrap justify-center max-w-4xl mx-auto">
      {[ ...Array( 12 ).keys() ].map( genre => <li key={genre}><Skeleton className="rounded-full w-[80px] h-[30px] " /> </li> )}
    </ul>
  )
}
