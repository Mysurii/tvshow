import { Input } from '@/common/components/ui/input'
import { Search } from 'lucide-react'
import { useEffect, useState } from 'react'
import { TvShowShowcase } from '@/common/types/tvshow'
import { searchForShows } from '@/common/services/api/tvshows'
import SearchCard from './searchCard'
import { cn } from '@/lib/utils'
import { Link } from 'react-router-dom'
import { PATH_CONSTANTS } from '@/common/constants/path'

export default function Searchbar () {
  const [ inputValue, setInputValue ] = useState( '' );
  const [ tvShows, setTvShows ] = useState<TvShowShowcase[]>( [] )

  useEffect( () => {
    const timeoutId = setTimeout( async () => {
      const response = await searchForShows( inputValue )
      setTvShows( response );
    }, 500 );
    return () => clearTimeout( timeoutId );
  }, [ inputValue ] );



  return (
    <div className='relative z-10'>
      <Input
        value={inputValue}
        placeholder="TvShow.."
        className='w-full'
        icon={<Search />}
        onChange={e => setInputValue( e.target.value )} />
      <div className={cn( 'absolute flex flex-col w-full h-min max-h-96 overflow-y-auto bg-black  border-t-0  ', [
        tvShows.length > 0 && 'p-2 border border-stone-800 gap-2 rounded'
      ] )}>
        {tvShows.map( tvShow => <Link to={`${ PATH_CONSTANTS.TV_SHOWS }/${ tvShow.id }`} onClick={() => setInputValue( '' )}>
          <SearchCard key={tvShow.id} tvShowCase={tvShow} />
        </Link> )}
      </div>
    </div>
  )
}
