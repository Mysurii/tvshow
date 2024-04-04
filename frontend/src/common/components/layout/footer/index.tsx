import reactSvg from '@/assets/react.svg'

export default function Footer () {
  return (
    <footer className="bg-background flex justify-center items-center gap-4 pt-12 pb-4 text-xs font-bold">
      <span className="text-emerald-500">Made with</span>
      <img src={reactSvg} alt="react" className='w-[25] h-[25]' />
      <span>by Ali Ozcan</span>
    </footer>
  )
}
