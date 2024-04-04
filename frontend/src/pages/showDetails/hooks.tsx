import { getTvShowById } from "@/common/services/api/tvshows";
import { useQuery } from "@tanstack/react-query";

const QUERY_KEY = [ 'tvshow-id' ];


export default function useTvShow ( id: number ) {

  const response = useQuery( { queryKey: QUERY_KEY, queryFn: () => getTvShowById( id ), staleTime: 60000 } );

  return response;
}
