import { AbvFormatPipe } from '@app/recipe/pipes/abv-format.pipe';

describe('AbvFormatPipe', () => {
  const pipe = new AbvFormatPipe();

  it('should format abv', async() => {
    expect(pipe.transform('12 %')).toBe('12');
  });
});
