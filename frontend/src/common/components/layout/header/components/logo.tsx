import { PATH_CONSTANTS } from "@/common/constants/path"
import { Link } from "react-router-dom"

export default function Logo () {
  return (
    <div className="font-bold text-2xl cursor-pointer transition-all hover:opacity-75">
      <Link to={PATH_CONSTANTS.HOME}>
        <span className="text-emerald-500">Tv</span>Shows
      </Link></div>
  )
}
