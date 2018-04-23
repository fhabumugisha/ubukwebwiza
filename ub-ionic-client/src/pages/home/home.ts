import { Provider } from "./../../models/provider.model";
import { ProvidersService } from "./../../services/providers.service";
import { Component } from "@angular/core";
import { NavController } from "ionic-angular";

@Component({
  selector: "page-home",
  templateUrl: "home.html"
})
export class HomePage {
  featuredProviders: Provider[];
  constructor(
    public navCtrl: NavController,
    public providersService: ProvidersService
  ) {}
  ionViewLod() {
    this.getFeaturedProviders();
  }
  getFeaturedProviders() {
    this.providersService
      .getFeaturedProviders()
      .subscribe((providers: Provider[]) => {
        this.featuredProviders = providers;
      });
  }
}
