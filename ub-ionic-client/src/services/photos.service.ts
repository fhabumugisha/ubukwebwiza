import { Injectable } from "@angular/core";
//import { environment } from "../environments/environment";
import { HttpClient } from "@angular/common/http";

@Injectable()
export class PhotosService {
  //public API = environment.production ? "https://ecoledudimanche.jelastic.lunacloud.com/api"  : "http://localhost:8080/ubukwebwiza/api";
  public API = "http://ecoledudimanche.jelastic.lunacloud.com/api";
  public PHOTOS_API = this.API + "/photos";
  constructor(private http: HttpClient) {
    console.log("Hello PhotosService ");
  }

  getAllPhotos() {
    console.log("get photos...");
    return this.http.get(this.PHOTOS_API);
  }
  getSliderPhotos() {
    return this.http.get(this.API + "/slider-photos");
  }
}
