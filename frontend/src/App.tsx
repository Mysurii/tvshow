
import { ProviderWrapper } from "./common/services/providers"
import Routes from "./router"
import { ErrorBoundary } from "react-error-boundary"
import ErrorDisplay from "./common/components/errorDisplay"

function App () {
  return (
    <ErrorBoundary fallback={ErrorDisplay()}>
      <ProviderWrapper>
        <Routes />
      </ProviderWrapper>
    </ErrorBoundary>
  )
}

export default App
