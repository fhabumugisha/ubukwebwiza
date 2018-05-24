import { PhotosService } from "./../../services/photos.service";
import { Provider } from "./../../models/provider.model";
import { ProvidersService } from "./../../services/providers.service";
import { Component } from "@angular/core";
import { NavController } from "ionic-angular";
import { Photo } from "../../models/photo.model";

@Component({
  selector: "page-home",
  templateUrl: "home.html"
})
export class HomePage {
  featuredProviders: Provider[];
  sliderPhotos: Photo[] = [];
  constructor(
    public navCtrl: NavController,
    public providersService: ProvidersService,
    private photosService: PhotosService
  ) {}
  ionViewWillEnter() {
    this.getFeaturedProviders();
    this.getSliderPhotos();
  }
  getFeaturedProviders() {
    this.providersService
      .getFeaturedProviders()
      .subscribe((providers: Provider[]) => {
        this.featuredProviders = providers;
      });
  }

  getSliderPhotos() {
    this.photosService.getSliderPhotos().subscribe((photos: Photo[]) => {
      this.sliderPhotos = photos;
    });
  }
}
