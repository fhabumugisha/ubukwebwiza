import { WeddingService } from "./wedding-service.model";
import { Photo } from "./photo.model";
export class Provider {
  public id: any;

  public businessName: string;

  public phoneNumber: string;

  public website?: string;

  public fbUsername?: string;

  public twitterUsername?: string;

  public profilPicture?: string;

  public aboutme?: string;

  public address?: string;

  public email: string;

  public district: string;

  public services: WeddingService[] = [];

  public photos?: Photo[] = [];

  constructor(
    id: any,
    businessName: string,
    phoneNumber: string,
    email: string,
    district: string,
    services: WeddingService[],
    photos?: Photo[],
    website?: string,
    fbUsername?: string,
    twitterUsername?: string,
    profilPicture?: string,
    aboutme?: string,
    address?: string
  ) {
    this.id = id;

    this.businessName = businessName;

    this.phoneNumber = phoneNumber;

    this.website = website;

    this.fbUsername = fbUsername;

    this.twitterUsername = twitterUsername;

    this.profilPicture = profilPicture;

    this.aboutme = aboutme;

    this.address = address;

    this.email = email;

    this.district = district;

    this.services = services;

    this.photos = photos;
  }
}
