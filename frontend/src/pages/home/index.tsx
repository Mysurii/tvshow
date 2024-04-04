import DisplayGenres from "./components/displayGenres";
import ShowList from "./components/showList"

export default function Home () {
  return (
    <main className="container flex flex-col p-4 lg:p-0">

      <section className=" py-24 lg:py-44">
        <header className="flex flex-col items-center justify-center text-center gap-4">
          <h1 className="font-bold text-4xl" data-testid="cypress-title">Welcome to TvShows</h1>
          <p>Discover your next favorite TV shows. Explore a wide range of genres and find top-rated shows recommended just for you.</p>
        </header>
      </section>

      <DisplayGenres />
      <ShowList name="Recommended" sort="recommended" />
      <ShowList name="Latest TV Shows" sort="latest" />
      <ShowList name="Recently updated" sort="updated" />
    </main>
  )
}
