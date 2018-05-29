import { Component, OnInit } from "@angular/core";
import {
  IonicPage,
  NavController,
  NavParams,
  PopoverController
} from "ionic-angular";
import { MyPopOverPage } from "../my-pop-over/my-pop-over";

/**
 * Generated class for the DetailMessagePage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: "page-detail-message",
  templateUrl: "detail-message.html"
})
export class DetailMessagePage implements OnInit {
  editorMsg = "";
  public threadName: string;
  ngOnInit(): void {
    this.threadName = this.navParams.get("threadName");
    console.log(this.threadName);
  }

  constructor(
    public navCtrl: NavController,
    public navParams: NavParams,
    public popoverCtrl: PopoverController
  ) {}

  ionViewDidLoad() {
    console.log("ionViewDidLoad DetailMessagePage");
    this.threadName = this.navParams.get("threadName");
  }

  switchEmojiPicker() {}

  sendMsg() {}
  onFocus() {}
  openModal() {}

  presentPopover(event) {
    let popover = this.popoverCtrl.create(MyPopOverPage);
    popover.present({ ev: event });
  }
}
