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
  searchTerm: string = '';
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

  getItems() {
    // Reset items back to all of the items
    this.getProviders();

    // set val to the value of the searchbar

console.log(this.searchTerm);
    // if the value is an empty string don't filter the items
    if (this.searchTerm && this.searchTerm.trim() != '') {
      this.providers = this.providers.filter((item) => {
        return (item.businessName.toLowerCase().indexOf(this.searchTerm.toLowerCase()) > -1);
      })
    }
  }
}
