import type { TvShowShowcase } from '@/common/types/tvshow'
import { motion } from 'framer-motion'
import NotFound from '../notFound'
import Showcase from '@/common/components/showcase'

type Props = {
  tvShows: TvShowShowcase[]
}

export default function DisplayTvShows ( { tvShows }: Props ) {

  if ( tvShows.length === 0 ) return <div className='h-44 grid place-content-center'><NotFound text='No shows available' /></div>

  return (
    <ul data-testid="tvshow-list" className="grid grid-cols-2 sm:grid-cols-4 md::grid-cols-5 lg:grid-cols-7 gap-4">
      {tvShows?.map( ( show, idx ) => <motion.li
        key={show.id}
        data-genre={show.genres}
        variants={fadeInAnimationVariants}
        initial="initial"
        whileInView="animate"
        viewport={{
          once: true
        }}
        custom={idx}
      >{<Showcase show={show} />}</motion.li> )}
    </ul>
  )
}


const fadeInAnimationVariants = {
  initial: {
    opacity: 0,
    y: -20
  },
  animate: ( idx: number ) => ( {
    opacity: 1, y: 0, transition: { staggerChildren: 0.5, delay: ( 0.1 * idx ) }
  } )
}