import { TVShow } from "@/common/types/tvshow"
import { Separator } from "@/common/components/ui/separator"
import { bottomToTop, defaultTransition, leftToRight, rightToLeft } from "@/lib/constants/animations"
import { motion } from 'framer-motion'

type Props = {
  tvShow: TVShow
}


export default function DisplayInfo ( { tvShow }: Props ) {
  return (
    <>
      {/* Apparantly image can be null.. */}
      {tvShow.image && <motion.img
        variants={leftToRight}
        initial="hidden"
        animate="visible"
        transition={{ ...defaultTransition }}
        src={tvShow.image.original}
        alt={tvShow.name}
        className="object-cover object-center max-w-xs max-h-[50vh] rounded border" />}
      <div data-testid="details">
        <motion.h1
          variants={bottomToTop}
          initial="hidden"
          animate="visible"
          transition={{ ...defaultTransition, delay: 0.1 }}
          className="text-2xl font-bold mb-2  ">{tvShow.name}</motion.h1>
        <motion.div
          variants={bottomToTop}
          initial="hidden"
          animate="visible"
          transition={{ ...defaultTransition, delay: 0.2 }}
          dangerouslySetInnerHTML={{ __html: tvShow.summary }} className="text-gray-500" />
        <motion.div
          variants={rightToLeft}
          initial="hidden"
          animate="visible"
          transition={{ ...defaultTransition, delay: 0.3 }}
        >
          <Separator className="my-4 " />
        </motion.div>

        <div className="flex gap-12  text-gray-500">
          <motion.ul
            variants={bottomToTop}
            initial="hidden"
            animate="visible"
            transition={{ ...defaultTransition, delay: 0.4 }}
            className="space-y-2">
            <li>Rating:</li>
            <li>Genres:</li>
            <li>Runtime:</li>
            <li>Language:</li>
            <li>Premiered:</li>
            <li>Last updated:</li>
          </motion.ul>

          <motion.ul
            variants={bottomToTop}
            initial="hidden"
            animate="visible"
            transition={{ ...defaultTransition, delay: 0.5 }}
            className="space-y-2">
            <li>{tvShow.rating.average}</li>
            <li>{tvShow.genres.map( genre => <span>{genre}, {' '}</span> )}</li>
            <li>{tvShow.averageRuntime}</li>
            <li>{tvShow.language}</li>
            <li>{tvShow.premiered.toDateString()}</li>
            <li>{convertUnixToHumanReadableDate( tvShow.updated )}</li>
          </motion.ul>
        </div>
      </div>
    </>
  )
}

function convertUnixToHumanReadableDate ( timestamp: number ) {
  return new Date( timestamp * 1000 ).toLocaleDateString()
}
