import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class JsonService {

  url : string;

  constructor(private http : HttpClient) {
    this.url = 'http://localhost:8080/' ;
  }

  getJson(url : string) {
    let options : Object =  {
      "observe" : 'body',
      "responseType": 'text'
    }
      url = this.url + url;
    return this.http.get(url, options);
  }

  postJson(url : string, body : Object){
    let options : Object =  {
          "observe" : 'body',
          "responseType": 'json'
    }
      url = this.url + url;
    return this.http.post(url, body, options);
  }
}
