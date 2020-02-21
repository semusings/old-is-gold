import { TestBed, inject } from '@angular/core/testing';

import { PathsalaHttpInterceptorService } from './http-interceptor.service';

describe('PathsalaHttpInterceptorService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PathsalaHttpInterceptorService]
    });
  });

  it('should be created', inject([PathsalaHttpInterceptorService], (service: PathsalaHttpInterceptorService) => {
    expect(service).toBeTruthy();
  }));
});
