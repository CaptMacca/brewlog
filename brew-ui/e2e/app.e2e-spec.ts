import { BrewlogUiPage } from './app.po';

describe('brewlog-ui App', () => {
  let page: BrewlogUiPage;

  beforeEach(() => {
    page = new BrewlogUiPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
