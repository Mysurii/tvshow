import { Link } from 'react-router-dom'
import { Card, CardContent } from '../ui/card'
import { TvShowShowcase } from '@/common/types/tvshow'
import { PATH_CONSTANTS } from '@/common/constants/path'

export default function Showcase ( { show }: { show: TvShowShowcase } ) {
  return (
    <Link to={`${ PATH_CONSTANTS.TV_SHOWS }/${ show.id }`}>
      <Card className="w-fit">
        <CardContent className="p-0 pb-2 text-center rounded cursor-pointer hover:opacity-75">
          <img src={show.image.medium} alt={show.name} className="object-cover w-full object-center rounded-t" />
          <small className="flex gap-1 py-1 items-center justify-center bg-stone-950  text-gray-600 text-[12px]]">
            {show.premiered.getFullYear()}
            <span>&#x2022;</span>
            {show.averageRuntime}min
          </small>
          <h4 className="my-2">{show.name}</h4>
        </CardContent>
      </Card>
    </Link>
  )
}
