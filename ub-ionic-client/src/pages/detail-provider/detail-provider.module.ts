import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { DetailProviderPage } from './detail-provider';

@NgModule({
  declarations: [
    DetailProviderPage,
  ],
  imports: [
    IonicPageModule.forChild(DetailProviderPage),
  ],
})
export class DetailProviderPageModule {}
