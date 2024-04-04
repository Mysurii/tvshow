import { TvShowShowcase } from '@/common/types/tvshow';
import useTvShows from '@/lib/hooks/useTvShows';
import DisplayLoadingState from '@/pages/home/components/showList/displayLoadingState';
import DisplayTvShows from '@/pages/home/components/showList/displayTvShows';

type Props = {
  page: number
  tvShows: TvShowShowcase[]
}

const PAGE_SIZE = 21

export default function DisplayList ( { tvShows, page }: Props ) {
  const { isLoading } = useTvShows();

  const startIndex = page * PAGE_SIZE;
  const endIndex = startIndex + PAGE_SIZE;
  const pageOfTvShows = tvShows?.slice( startIndex, endIndex );

  return (
    <section className="flex flex-col gap-2 my-4">
      {isLoading ? <DisplayLoadingState /> : <DisplayTvShows tvShows={pageOfTvShows || []} />}
    </section>
  )
}
