import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "../environments/environment";

/*
  Generated class for the PhotosProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class PhotosService {
  public API = environment.production ? 'https://buidenv.jelastic.lunacloud.com/api' : 'http://localhost:8080/api';
  //public API = "localhost:8080/ubukwebiza/api";
  public PHOTOS_API = this.API + "/photos";
  constructor(public http: HttpClient) {
    console.log("Hello PhotosService ");
  }

  getAllPhotos() {
    return this.http.get(this.PHOTOS_API);
  }
  getSliderPhotos() {
    return this.http.get(this.API + "/sliderPhotos");
  }
}
