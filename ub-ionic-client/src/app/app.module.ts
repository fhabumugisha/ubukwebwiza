import { DetailPhotoPage } from "./../pages/detail-photo/detail-photo";
import { DetailProviderPage } from "./../pages/detail-provider/detail-provider";
import { NgModule, ErrorHandler } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { IonicApp, IonicModule, IonicErrorHandler } from "ionic-angular";
import { MyApp } from "./app.component";

import { HomePage } from "../pages/home/home";
import { TabsPage } from "../pages/tabs/tabs";

import { StatusBar } from "@ionic-native/status-bar";
import { SplashScreen } from "@ionic-native/splash-screen";
import { ProvidersPage } from "../pages/providers/providers";
import { PhotosPage } from "../pages/photos/photos";
import { ContactUsPage } from "../pages/contact-us/contact-us";
import { SettingsPage } from "../pages/settings/settings";
import { PhotosService } from "../services/photos.service";
import { ProvidersService } from "../services/providers.service";
import { HttpClientModule } from "@angular/common/http";
import { MessagesPage } from "../pages/messages/messages";
import { DetailMessagePage } from "../pages/detail-message/detail-message";
import { MyPopOverPage } from "../pages/my-pop-over/my-pop-over";

@NgModule({
  declarations: [
    MyApp,
    PhotosPage,
    ProvidersPage,
    HomePage,
    TabsPage,
    DetailProviderPage,
    ContactUsPage,
    SettingsPage,
    DetailPhotoPage,
    MessagesPage,
    DetailMessagePage,
    MyPopOverPage
  ],
  imports: [BrowserModule, HttpClientModule, IonicModule.forRoot(MyApp)],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    PhotosPage,
    ProvidersPage,
    HomePage,
    TabsPage,
    DetailProviderPage,
    ContactUsPage,
    SettingsPage,
    DetailPhotoPage,
    MessagesPage,
    DetailMessagePage,
    MyPopOverPage
  ],
  providers: [
    StatusBar,
    SplashScreen,
    { provide: ErrorHandler, useClass: IonicErrorHandler },
    ProvidersService,
    PhotosService
  ]
})
export class AppModule {}
