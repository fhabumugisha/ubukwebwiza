import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { MyPopOverPage } from './my-pop-over';

@NgModule({
  declarations: [
    MyPopOverPage,
  ],
  imports: [
    IonicPageModule.forChild(MyPopOverPage),
  ],
})
export class MyPopOverPageModule {}
