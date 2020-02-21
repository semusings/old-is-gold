import { PathsalaModule } from './pathsala.module';

describe('PathsalaModule', () => {
  let pathsalaModule: PathsalaModule;

  beforeEach(() => {
    pathsalaModule = new PathsalaModule();
  });

  it('should create an instance', () => {
    expect(pathsalaModule).toBeTruthy();
  });
});
