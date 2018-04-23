export class Photo {
  public id: any;
  public title: string;
  public description?: string;
  public idProvider: number;

  constructor(
    id: number,
    title: string,
    idProvider: number,
    description?: string
  ) {
    this.id = id;
    this.idProvider = idProvider;
    this.title = title;
    this.description = description;
  }
}
