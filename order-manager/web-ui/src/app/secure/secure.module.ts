import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SecureRoutingModule} from './secure-routing.module';
import {ClarityModule} from '@clr/angular';
import {DasboardComponent} from './dasboard/dasboard.component';
import { MainComponent } from './main.component';

@NgModule({
  imports: [
    CommonModule,
    ClarityModule,
    SecureRoutingModule
  ],
  declarations: [DasboardComponent, MainComponent]
})
export class SecureModule {

}
