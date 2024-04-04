import { useParams } from "react-router-dom"
import useTvShow from "./hooks";
import DisplayLoadingState from "./components/displayLoadingState";
import { useEffect } from "react";
import DisplayInfo from "./components/displayInfo";
import NotFound from "../home/components/notFound";

export default function ShowDetails () {
  const params = useParams()
  const { id } = params;
  const { data: tvShow, isLoading, refetch } = useTvShow( parseInt( id ?? '0' ) || 0 );

  useEffect( () => {
    refetch()
  }, [ id, refetch ] )


  if ( isLoading ) return <DisplayLoadingState />

  if ( !tvShow ) return <div data-testid="not found" className="grid place-content-center h-[75vh]"><NotFound text="Could not find the tvshow." /></div>



  return (
    <main className="container min-h-[80vh] flex flex-col md:flex-row gap-12 justify-center items-center">
      <DisplayInfo tvShow={tvShow} />
    </main>
  )
}

