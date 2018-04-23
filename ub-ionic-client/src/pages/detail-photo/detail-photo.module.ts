import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { DetailPhotoPage } from './detail-photo';

@NgModule({
  declarations: [
    DetailPhotoPage,
  ],
  imports: [
    IonicPageModule.forChild(DetailPhotoPage),
  ],
})
export class DetailPhotoPageModule {}
