import { TvShowShowcase } from '@/common/types/tvshow';

export function filterByGenre(
  data: TvShowShowcase[],
  genre: string
): TvShowShowcase[] {
  return data?.filter((x) =>
    x.genres.map((y) => y.toLowerCase()).includes(genre)
  );
}

export function orderByRating(
  data: TvShowShowcase[],
  genre: string
): TvShowShowcase[] {
  return data?.filter((x) =>
    x.genres.map((y) => y.toLowerCase()).includes(genre)
  );
}
