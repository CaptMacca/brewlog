import { AppModule } from '@app/app.module';
import { FaIconLibrary } from '@fortawesome/angular-fontawesome';

describe('BrewModule', () => {
  let appModule: AppModule;
  let library: FaIconLibrary

  beforeEach(() => {
    library = new FaIconLibrary();
    appModule = new AppModule(library);
  });

  it('should create an instance', () => {
    expect(AppModule).toBeTruthy();
  });
});
