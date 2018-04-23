import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "../environments/environment";

/*
  Generated class for the ProvidersProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class ProvidersService {
  public API = environment.production
    ? "https://buidenv.jelastic.lunacloud.com/api"
    : "http://localhost:8080/api";

  // public API = "localhost:8080/ubukwebiza/api";
  public PROVIDERS_API = this.API + "/providers";
  constructor(public http: HttpClient) {
    console.log("Hello ProvidersProvider Provider");
  }

  getAllProviders() {
    return this.http.get(this.PROVIDERS_API);
  }

  getFeaturedProviders() {
    return this.http.get(this.API + "/featured-providers");
  }
}
