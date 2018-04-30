import { PhotosService } from "./../../services/photos.service";
import { Component } from "@angular/core";
import { NavController } from "ionic-angular";
import { Photo } from "../../models/photo.model";

@Component({
  selector: "page-photos",
  templateUrl: "photos.html"
})
export class PhotosPage {
  photos: Photo[] = [];
  constructor(
    public navCtrl: NavController,
    private photosService: PhotosService
  ) {}

  ionViewWillEnter() {
    this.photosService.getAllPhotos().subscribe((photos: Photo[]) => {
      this.photos = photos;
    });
  }
}
