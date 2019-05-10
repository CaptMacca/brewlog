import { BrewModule } from './brew.module';

describe('BrewModule', () => {
  let brewsModule: BrewModule;

  beforeEach(() => {
    brewsModule = new BrewModule();
  });

  it('should create an instance', () => {
    expect(brewsModule).toBeTruthy();
  });
});
