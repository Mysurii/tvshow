import Header from './header'
import { Outlet } from 'react-router-dom'
import Footer from './footer'
import { Suspense } from 'react'
import Loading from '@/common/components/loading'

export default function Layout () {
  return (
    <>
      <Header />
      <Suspense fallback={<Loading />}>
        <Outlet />
      </Suspense>
      <Footer />
    </>
  )
}
