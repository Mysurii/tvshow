import axios from 'axios';
import { EnvVariables } from './env-variables';

export default axios.create({
  baseURL: EnvVariables.API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});
