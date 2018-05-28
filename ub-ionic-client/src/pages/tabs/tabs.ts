import { ContactUsPage } from "./../contact-us/contact-us";
import { Component } from "@angular/core";

import { PhotosPage } from "../photos/photos";
import { ProvidersPage } from "../providers/providers";
import { MessagesPage } from "../messages/messages";

@Component({
  templateUrl: "tabs.html"
})
export class TabsPage {
  tab3Root = MessagesPage;
  tab1Root = ProvidersPage;
  tab2Root = PhotosPage;
  tab4Root = ContactUsPage;
  constructor() {}
}
