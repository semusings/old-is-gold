import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {PathsalaRoutingModule} from './pathsala-routing.module';
import {MainComponent} from './main.component';
import {ClarityModule} from '@clr/angular';

@NgModule({
  imports: [
    CommonModule,
    ClarityModule,
    PathsalaRoutingModule
  ],
  declarations: [MainComponent]
})
export class PathsalaModule {
}
