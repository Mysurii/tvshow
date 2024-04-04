import { BaseEntity } from './baseEntity';

export type TVShow = BaseEntity & {
  name: string;
  summary: string;
  genres: string[];
  rating: Rating;
  averageRuntime: number;
  image: Image;
  premiered: Date;
  updated: number;
  language: string;
};

export type TvShowShowcase = Omit<TVShow, 'summary'>;

type Image = {
  medium: string;
  original: string;
};

type Rating = {
  average: number;
};
