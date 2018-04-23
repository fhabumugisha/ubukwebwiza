export class WeddingService {
  public id: any;
  public title: string;
  public description?: string;
  constructor(id: any, title: string, description?: string) {
    this.id = id;
    this.title = title;
    this.description = description;
  }
}
