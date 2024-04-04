import { PATH_CONSTANTS } from '@/common/constants/path'


export default function ErrorDisplay () {
  return (
    <section className="h-screen w-screen flex flex-col justify-center items-center font-bold">
      <h1 className="text-2xl mb-2">500 - Server Error</h1>
      <p className='text-center mb-2'>Oops! Something went wrong on our end. The page you're looking for may not be available at the moment.</p>
      <span>Feel free to go back to the <a href={PATH_CONSTANTS.HOME} className="text-green-500 underline">homepage</a>.</span>
    </section>
  )
}
