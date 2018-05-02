import { DetailProviderPage } from "./../detail-provider/detail-provider";
import { Provider } from "./../../models/provider.model";
import { Component } from "@angular/core";
import { NavController } from "ionic-angular";
import { ProvidersService } from "../../services/providers.service";

@Component({
  selector: "page-providers",
  templateUrl: "providers.html"
})
export class ProvidersPage {
  providers: Provider[] = [];
  constructor(
    public navCtrl: NavController,
    public providersService: ProvidersService
  ) {}

  ionViewWillEnter() {
    this.getProviders();
  }

  getProviders() {
    this.providersService
      .getAllProviders()
      .subscribe((providers: Provider[]) => {
        this.providers = providers;
      });
  }

  onViewProvider(provider: Provider) {
    this.navCtrl.push(DetailProviderPage, { provider: provider });
  }
}
