import { TvShowShowcase } from '@/common/types/tvshow'


type Props = {
  tvShowCase: TvShowShowcase
}

export default function SearchCard ( { tvShowCase }: Props ) {
  return (
    <div className='flex gap-4 cursor-pointer hover:bg-gray-900'>
      <img src={tvShowCase.image?.medium} alt={tvShowCase.name} className='w-[40px] object-cover object-center' />
      <div className=''>
        <span>{tvShowCase.name}</span>
        <small className="flex gap-1 py-1 items-center  bg-stone-950  text-gray-600 text-[12px]]">
          {tvShowCase.premiered.getFullYear()}
          <span>&#x2022;</span>
          {tvShowCase.averageRuntime}min
        </small>
      </div>
    </div>
  )
}
