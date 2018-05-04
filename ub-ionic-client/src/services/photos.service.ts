import { Injectable } from "@angular/core";
import { environment } from "../environments/environment";
import { HttpClient } from "@angular/common/http";

@Injectable()
export class PhotosService {
  public API = environment.production
    ? "https://ub-core-api.jelastic.lunacloud.com/ubukwebwiza/api"
    : "http://localhost:8080/ubukwebwiza/api";
  //public API = "localhost:8080/ubukwebiza/api";
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
