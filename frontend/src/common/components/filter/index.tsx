import { PATH_CONSTANTS } from "@/common/constants/path"
import { getUniqueGenres } from "@/common/utils/tvshow-utils"
import {
  Sheet,
  SheetContent,
  SheetDescription,
  SheetHeader,
  SheetTitle,
  SheetTrigger,
} from "@/common/components/ui/sheet"
import { AlignRight } from "lucide-react"
import { Link } from "react-router-dom"
import useTvShows from "@/lib/hooks/useTvShows"


export default function Filter () {
  const { data: tvShows } = useTvShows()
  const genres = getUniqueGenres( tvShows )

  return (
    <Sheet>
      <SheetTrigger><AlignRight className="hover:text-green-500 hover:scale-110 transition-all duration-150" /></SheetTrigger>
      <SheetContent>
        <SheetHeader>
          <SheetTitle>TvShows</SheetTitle>
          <SheetDescription>
            <span>Search through your favorite tvshows by genre</span>
            <ul className="grid grid-cols-2 gap-2 capitalize text-lg overflow-y-auto mt-12">
              {genres.map( genre => <li key={genre} className="cursor-pointer hover:text-green-500"><Link to={`${ PATH_CONSTANTS.TV_SHOWS }?genre=${ genre.toLowerCase() }`}>{genre}</Link></li> )}
            </ul>
          </SheetDescription>
        </SheetHeader>
      </SheetContent>
    </Sheet>
  )
}
