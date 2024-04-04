import { TvShowShowcase } from '@/common/types/tvshow';

// Sort TV shows by premiered date
export const sortByPremiered = (a: TvShowShowcase, b: TvShowShowcase) => {
  return b.premiered.getTime() - a.premiered.getTime();
};

// Sort TV shows by rating
export const sortByRating = (a: TvShowShowcase, b: TvShowShowcase) => {
  return b.rating.average - a.rating.average;
};

// Sort TV shows by updated date
export const sortByUpdated = (a: TvShowShowcase, b: TvShowShowcase) => {
  return a.updated - b.updated;
};

export const getUniqueGenres = (tvShows: TvShowShowcase[] | undefined) => {
  const uniqueGenres = new Set<string>();
  tvShows?.forEach((tvShow) => {
    tvShow.genres.forEach((genre) => uniqueGenres.add(genre));
  });
  return Array.from(uniqueGenres).sort();
};
