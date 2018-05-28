import { Component, OnInit } from "@angular/core";
import { IonicPage, NavController, NavParams } from "ionic-angular";

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

  constructor(public navCtrl: NavController, public navParams: NavParams) {}

  ionViewDidLoad() {
    console.log("ionViewDidLoad DetailMessagePage");
    this.threadName = this.navParams.get("threadName");
  }

  switchEmojiPicker() {}

  sendMsg() {}
  onFocus() {}
}
