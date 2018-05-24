import { Provider } from "./../../models/provider.model";
import { Component, OnInit } from "@angular/core";
import { IonicPage, NavController, NavParams } from "ionic-angular";
import { ProvidersService } from "../../services/providers.service";

/**
 * Generated class for the DetailProviderPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: "page-detail-provider",
  templateUrl: "detail-provider.html"
})
export class DetailProviderPage implements OnInit {
  provider: Provider;
  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public providersService: ProvidersService
  ) {}

  ngOnInit(): void {
    this.provider = this.navParams.get("provider");
  }
  ionViewDidLoad() {
    console.log("ionViewDidLoad DetailProviderPage");
    console.log(this.navParams.get("provider"));
    this.provider = this.navParams.get("provider");
  }
}
