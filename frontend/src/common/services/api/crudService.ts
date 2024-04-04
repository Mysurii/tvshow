import http from '@/config/http-common';

async function getAll<T>(uri: string) {
  const response = await http.get<Array<T>>(uri);
  return response.data as T;
}

async function get<T>(uri: string) {
  const response = await http.get<T>(uri);
  return response.data as T;
}

const api = { get, getAll };

export default api;
