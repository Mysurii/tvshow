# Frontend Assignment

**This is a frontend assignment of ABN AMRO. The frontend is made with ReactJs, TailwindCSS & Shadcn-UI**

## Running the Application Locally

To run the application locally, follow these steps:

### Prerequisites

- Node
- NPM || Yarn
- Docker (Optional)

### Instructions

1. Navigate to the project directory:

```
cd frontend
```

2. Install the modules using the following command:

```
npm install || yarn install
```

3. Run the application:

```
npm run dev || yarn dev
```

4. Access the application in your web browser | http://localhost:5173/

5. Run tests with the following commands:

unit tests:

```
npm run test || yarn test
```

e2e tests (the server must be running to be able to test e2e tests with cypress (see step 3)):

```
npx cypress run (or npx cypress open to open UI)
```

### Notes

- In the .env file, there must be a variable 'VITE_API_BASE_URL'. The variable is already proided with its url.
- I only made some unit tests for the homepage, since the assignment is to show that I understand testing in general.
  Unit tests are also made in the backend project.
- There is a Dockerfile available if needed to run with Docker.
