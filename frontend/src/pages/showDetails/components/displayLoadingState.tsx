import { Loader } from 'lucide-react'

export default function DisplayLoadingState () {
  return (
    <Loader data-testid="loading-state" className='animate-spin min-h-[80vh] grid place-content-center' />
  )
}
