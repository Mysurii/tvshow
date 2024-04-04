import { render, screen } from '@testing-library/react'
import Home from '.'
import { QueryClient, QueryClientProvider } from '@tanstack/react-query'
import { MemoryRouter } from 'react-router-dom';
import DisplayTvShows from './components/showList/displayTvShows';
import { TvShowShowcase } from '@/common/types/tvshow';

const intersectionObserverMock = () => ( {
  observe: () => null
} )
window.IntersectionObserver = vi.fn().mockImplementation( intersectionObserverMock );

describe( 'Home page', () => {
  const queryClient = new QueryClient();
  const homePage = (
    <MemoryRouter>
      <QueryClientProvider client={queryClient}>
        <Home />
      </QueryClientProvider>
    </MemoryRouter>
  )

  it( 'should display the correct title', () => {
    render( homePage )
    const linkElement = screen.queryByText( /Welcome to TvShows/ )
    expect( linkElement ).toBeVisible()
  } )

  it( 'should render "No shows available" message when tvShows array is empty', () => {
    render( <DisplayTvShows tvShows={[]} /> );
    const notFoundElement = screen.getByText( 'No shows available' );
    expect( notFoundElement ).toBeInTheDocument();
  } );

  it( 'Should render a list of TV shows when tvShows array is not empty', () => {
    const mockTvShows = [
      { id: 1, name: 'Show 1', genres: [ 'Action', 'Drama' ], image: { medium: 'test' }, premiered: new Date(), averageRuntime: 60, },
    ] as unknown as TvShowShowcase[];

    render( <MemoryRouter><DisplayTvShows tvShows={mockTvShows} /> </MemoryRouter> );

    const tvShowListElement = screen.getByTestId( 'tvshow-list' );
    expect( tvShowListElement ).toBeInTheDocument();

    const tvShowItems = screen.getAllByRole( 'listitem' );
    expect( tvShowItems.length ).toBe( mockTvShows.length );

    mockTvShows.forEach( ( tvShow, index ) => {
      const tvShowItem = tvShowItems[ index ];
      expect( tvShowItem ).toHaveAttribute( 'data-genre', tvShow.genres.join( ',' ) );
    } );
  } );
} )