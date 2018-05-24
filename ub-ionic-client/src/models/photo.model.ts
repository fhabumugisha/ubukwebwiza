export class Photo {
  public idPhoto: any;
  public filename: string;
  public description?: string;
  public idProvider?: number;
  public providerUrl?: string;
  public providerBusinessName?: string;

  constructor(
    idPhoto: number,
    filename: string,
    idProvider: number,
    providerUrl?: string,
    providerBusinessName?: string,
    description?: string
  ) {
    this.idPhoto = idPhoto;
    this.idProvider = idProvider;
    this.filename = filename;
    this.providerUrl = providerUrl;
    this.providerBusinessName = providerBusinessName;
    this.description = description;
  }
}
