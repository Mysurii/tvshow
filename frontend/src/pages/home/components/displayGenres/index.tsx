import { getUniqueGenres } from "@/common/utils/tvshow-utils";
import GenreSkeleton from "./genreSkeleton";

import DisplayGenresList from "./displayGenresList";
import useTvShows from "@/lib/hooks/useTvShows";

export default function DisplayGenres () {
  const { data: tvShows, isLoading } = useTvShows();

  if ( isLoading ) return <GenreSkeleton />;

  const genres = getUniqueGenres( tvShows );

  return (
    <section>
      <h3 className="text-center text-emerald-500 font-bold mb-4 uppercase">
        Genres
      </h3>
      <DisplayGenresList genres={genres} />
    </section>
  );
}
