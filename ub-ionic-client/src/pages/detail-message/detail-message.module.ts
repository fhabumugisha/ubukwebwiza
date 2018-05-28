import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { DetailMessagePage } from './detail-message';

@NgModule({
  declarations: [
    DetailMessagePage,
  ],
  imports: [
    IonicPageModule.forChild(DetailMessagePage),
  ],
})
export class DetailMessagePageModule {}
