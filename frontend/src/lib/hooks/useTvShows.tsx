import { getTvShows } from "@/common/services/api/tvshows";
import { useQuery } from "@tanstack/react-query";

export default function useTvShows () {
  const { data, isError, isLoading } = useQuery( { queryKey: [ 'tv-shows' ], queryFn: getTvShows, staleTime: 600000 } )

  return { data, isError, isLoading }
}
