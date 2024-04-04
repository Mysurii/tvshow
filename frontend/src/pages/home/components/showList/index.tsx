import type { TvShowShowcase } from '@/common/types/tvshow';
import { MoveRight } from 'lucide-react';
import DisplayLoadingState from './displayLoadingState';
import DisplayTvShows from './displayTvShows';
import { sortByPremiered, sortByRating, sortByUpdated } from '@/common/utils/tvshow-utils';
import { Link } from 'react-router-dom';
import { PATH_CONSTANTS } from '@/common/constants/path';
import useTvShows from '@/lib/hooks/useTvShows';

type SortType = 'recommended' | 'latest' | 'updated'

type Props = {
  name: string
  sort: SortType
}

const getSortFunction = ( sort: SortType ): ( a: TvShowShowcase, b: TvShowShowcase ) => number => {
  let sortFunction = sortByRating
  switch ( sort ) {
    case 'latest':
      sortFunction = sortByPremiered
      break
    case 'updated':
      sortFunction = sortByUpdated
      break
    default:
      sortFunction = sortByRating
  }
  return sortFunction
}

export default function ShowList ( { name, sort }: Props ) {
  const { data: tvShows, isLoading } = useTvShows();
  const sortedTvShows = tvShows?.sort( getSortFunction( sort ) ).slice( 0, 7 );


  return (
    <section className="flex flex-col gap-2 my-4">
      <div className="flex justify-between text-emerald-500 my-4">
        <h3 className="text-xl font-bold uppercase">{name}</h3>
        <Link
          to={`${ PATH_CONSTANTS.TV_SHOWS }?order=${ sort }`}
          className="group flex gap-2 items-center w-fit">
          See more  <MoveRight className="group-hover:mr-2 transition-all duration-150" />
        </Link>
      </div>
      {isLoading ? <DisplayLoadingState /> : <DisplayTvShows tvShows={sortedTvShows || []} />}
    </section>
  )
}
