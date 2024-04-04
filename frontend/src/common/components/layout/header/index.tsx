import Filter from "@/common/components/filter";
import Logo from "./components/logo";
import Searchbar from "./components/searchbar";

export default function Header () {
  return (
    <header>
      <nav className="container grid grid-cols-2 md:flex md:justify-between items-center p-4 gap-6 sm:gap-12">
        <Logo />
        <div className="order-last md:order-none max-w-xl w-full col-span-2">
          <Searchbar />
        </div>
        <div className="ml-auto md:m-0">
          <Filter />
        </div>
      </nav>
    </header>
  )
}
