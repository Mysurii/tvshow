import { PATH_CONSTANTS } from "@/common/constants/path";

export default function NotFoundPage () {
  return (
    <section className="h-[80vh] w-screen flex flex-col justify-center items-center font-bold">
      <h1 className="text-2xl mb-2">404 - Not Found</h1>
      <p>The page you're looking for doesn&apos;t exist.</p>
      <span>Go back to the <a href={PATH_CONSTANTS.HOME} className="text-green-500 underline">homepage</a>.</span>
    </section>
  )
}
