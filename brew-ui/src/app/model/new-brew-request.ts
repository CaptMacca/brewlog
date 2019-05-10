import { Brew } from '@app/model/brew';

export interface NewBrewRequest {
  username: string;
  brew: Brew;
}
