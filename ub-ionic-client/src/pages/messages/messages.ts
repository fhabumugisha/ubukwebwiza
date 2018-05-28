import { DetailMessagePage } from "./../detail-message/detail-message";
import { Component } from "@angular/core";
import { IonicPage, NavController, NavParams } from "ionic-angular";

/**
 * Generated class for the MessagesPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: "page-messages",
  templateUrl: "messages.html"
})
export class MessagesPage {
  constructor(public navCtrl: NavController, public navParams: NavParams) {}

  ionViewDidLoad() {
    console.log("ionViewDidLoad MessagesPage");
  }

  onReadMessage(threadName: string) {
    this.navCtrl.push(DetailMessagePage, { threadName: threadName });
  }
}
