import ShowcaseSkeleton from '@/common/components/showcase/showcaseSkeleton'

export default function DisplayLoadingState () {
  return (
    <ul data-testid="loading-state" className="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-6 gap-4">
      {[ ...Array( 6 ).keys() ].map( ( key ) => <li key={key}>{<ShowcaseSkeleton />}</li> )}
    </ul>
  )
}
