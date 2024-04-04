import ListPagination from "./components/listPagination";
import { useSearchParams } from "react-router-dom";
import DisplayList from "./components/displayList";
import { filterByGenre } from "../home/utils";
import { sortByPremiered, sortByRating, sortByUpdated } from "@/common/utils/tvshow-utils";
import useTvShows from "@/lib/hooks/useTvShows";

export default function TvShows () {
  const { data: tvShows } = useTvShows()
  const [ searchParams ] = useSearchParams()

  const genre = searchParams.get( 'genre' );
  const page = searchParams.get( 'page' );
  const order = searchParams.get( 'order' );

  const currentPage = parseInt( page || '0' ) || 0

  const showsToDisplay = getfilteredAndOrderedShows();

  function getfilteredAndOrderedShows () {
    if ( !tvShows ) return [];

    if ( genre ) {
      return filterByGenre( tvShows, genre );
    }

    let sortFunction = sortByRating
    switch ( order ) {
      case 'latest':
        sortFunction = sortByPremiered
        break
      case 'updated':
        sortFunction = sortByUpdated
        break
      default:
        sortFunction = sortByRating
    }

    return tvShows.sort( sortFunction )
  }

  return (
    <main className="container min-h-[75vh]  p-4 lg:p-0">
      <h1 className="text-xl font-bold uppercase  text-emerald-500 mt-12">{genre ?? order ?? 'All Shows'}</h1>
      <DisplayList page={currentPage} tvShows={showsToDisplay || []} />
      <ListPagination currentPage={currentPage} />
    </main>
  )
}


