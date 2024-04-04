import React from 'react';
import { PATH_CONSTANTS } from "@/common/constants/path";
import { RouteObject, RouterProvider, createBrowserRouter } from 'react-router-dom';
import Layout from '@/common/components/layout';
import TvShowsPage from '@/pages/tvshows';
import ErrorDisplay from '@/common/components/errorDisplay';
import NotFoundPage from '@/pages/notFoundPage';

const HomePage = React.lazy( () => import( '@/pages/home' ) )
const TvShowDetailsPage = React.lazy( () => import( '@/pages/showDetails' ) )

const routes: RouteObject[] = [
  { path: PATH_CONSTANTS.HOME, element: <HomePage /> },
  { path: PATH_CONSTANTS.TV_SHOWS, element: <TvShowsPage /> },
  { path: PATH_CONSTANTS.TV_SHOW_DETAIL, element: <TvShowDetailsPage /> },
  { path: '*', element: <NotFoundPage /> }
]


export default function Routes () {
  const router = createBrowserRouter( [
    {
      element: <Layout />,
      errorElement: <ErrorDisplay />,
      children: routes
    }
  ] );
  return <RouterProvider router={router} />
}