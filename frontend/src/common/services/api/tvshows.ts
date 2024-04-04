import { TVShow, TvShowShowcase } from '@/common/types/tvshow';
import api from './crudService';

type Successresponse = {
  show: TvShowShowcase;
};

export async function getTvShowById(
  showId: TVShow['id']
): Promise<TVShow | null> {
  try {
    const response = await api.get<TVShow>(`/shows/${showId}`);
    response.premiered = new Date(response.premiered);
    return response;
  } catch (err: unknown) {
    return null;
  }
}

export async function getTvShows(): Promise<TvShowShowcase[]> {
  try {
    const response = await api.getAll<TvShowShowcase[]>('/shows');
    // Use setTimeout to introduce a delay of 1 second
    await new Promise((resolve) => setTimeout(resolve, 1000));
    return response.map((x) => ({ ...x, premiered: new Date(x.premiered) }));
  } catch (err: unknown) {
    return [];
  }
}

export async function searchForShows(query?: string) {
  try {
    const response = await api.getAll<Successresponse[]>(
      `/search/shows?q=${query}`
    );
    const shows = response.map((res) => res.show);

    return shows.map((x) => ({
      ...x,
      premiered: new Date(x.premiered),
    }));
  } catch (err: unknown) {
    return [];
  }
}
