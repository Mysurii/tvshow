const { VITE_API_BASE_URL } = import.meta.env;

if (!VITE_API_BASE_URL) throw new Error('API_BASE_URL is not set yet.');

type EnvVariables = {
  API_BASE_URL: string;
};

export const EnvVariables: EnvVariables = {
  API_BASE_URL: VITE_API_BASE_URL,
} as const;
