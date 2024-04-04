import { Badge } from "@/common/components/ui/badge"
import NotFound from "../notFound"
import { Link } from "react-router-dom"
import { PATH_CONSTANTS } from "@/common/constants/path"

type Props = {
  genres: string[]
}

export default function DisplayGenresList ( { genres }: Props ) {
  if ( genres.length === 0 ) return (
    <div className="text-center">
      <NotFound text="No genres found." />
    </div>
  )

  return (
    <ul data-testid="display-genres" className="flex gap-2 flex-wrap justify-center max-w-4xl mx-auto">
      {genres.map( ( genre, idx ) => (
        <li key={genre} data-testid={`genre-${ idx }`}>
          <Link to={`${ PATH_CONSTANTS.TV_SHOWS }?genre=${ genre.toLowerCase() }`}>
            <Badge
              variant="outline"
              className="cursor-pointer transition duration-150 hover:scale-105 hover:border-emerald-500"
            >
              {genre}
            </Badge>{" "}
          </Link>

        </li>
      ) )}
    </ul>
  )
}
