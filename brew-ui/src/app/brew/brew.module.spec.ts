import { BrewModule } from '@app/brew/brew.module';

describe('BrewModule', () => {
  let brewsModule: BrewModule;

  beforeEach(() => {
    brewsModule = new BrewModule();
  });

  it('should create an instance', () => {
    expect(brewsModule).toBeTruthy();
  });
});
