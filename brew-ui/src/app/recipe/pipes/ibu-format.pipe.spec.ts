import { IbuFormatPipe } from '@app/recipe/pipes/ibu-format.pipe';

describe('IbuFormatPipe', () => {
  const pipe = new IbuFormatPipe();

  it('should format abv', async() => {
    expect(pipe.transform('12 IBU')).toBe('12');
    expect(pipe.transform('12 IBUs')).toBe('12');
  });
});
