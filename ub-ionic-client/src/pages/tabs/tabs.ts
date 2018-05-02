import { ContactUsPage } from "./../contact-us/contact-us";
import { Component } from "@angular/core";

import { HomePage } from "../home/home";
import { PhotosPage } from "../photos/photos";
import { ProvidersPage } from "../providers/providers";

@Component({
  templateUrl: "tabs.html"
})
export class TabsPage {
  tab3Root = ContactUsPage;
  tab1Root = ProvidersPage;
  tab2Root = PhotosPage;

  constructor() {}
}
