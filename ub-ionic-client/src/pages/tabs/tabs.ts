import { Component } from "@angular/core";

import { HomePage } from "../home/home";
import { PhotosPage } from "../photos/photos";
import { ProvidersPage } from "../providers/providers";

@Component({
  templateUrl: "tabs.html"
})
export class TabsPage {
  tab1Root = HomePage;
  tab2Root = ProvidersPage;
  tab3Root = PhotosPage;

  constructor() {}
}
