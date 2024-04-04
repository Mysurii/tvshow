import {
  Pagination,
  PaginationContent,
  PaginationEllipsis,
  PaginationItem,
  PaginationNext,
  PaginationPrevious,
} from "@/common/components/ui/pagination"
import useTvShows from "@/lib/hooks/useTvShows"

type Props = {
  currentPage: number
}

export default function ListPagination ( { currentPage }: Props ) {
  const { isLoading } = useTvShows()

  if ( isLoading ) return null


  const getNewPage = ( type: 'prev' | 'next' ): URL => {
    const url = new URL( location.href )
    url.searchParams.delete( 'page' )

    if ( type === 'next' )
      url.searchParams.append( 'page', ( currentPage + 1 ).toString() )

    if ( type === 'prev' && currentPage > 0 )
      url.searchParams.append( 'page', ( currentPage - 1 ).toString() )

    return url;
  }


  return (
    <Pagination>
      <PaginationContent>
        {currentPage > 0 && <PaginationItem>
          <PaginationPrevious href={getNewPage( 'prev' ).href} />
        </PaginationItem>}
        <PaginationItem>
          <PaginationEllipsis />
        </PaginationItem>
        <PaginationItem>
          <PaginationNext href={getNewPage( 'next' ).href} />
        </PaginationItem>
      </PaginationContent>
    </Pagination>
  )
}
