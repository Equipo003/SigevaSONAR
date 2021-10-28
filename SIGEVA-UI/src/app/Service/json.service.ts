import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class JsonService {

  url : string;

  constructor(private http : HttpClient) {
    this.url = 'http://localhost:8080/' ;
  }

  getJson(url : string):Observable<any>{
    let options : Object =  {
      "observe" : 'body',
      "responseType": 'text'
    }
      url = this.url + url;
    return this.http.get(url, options);
  }

  putJsonVacunas(url : string, parameter1 : String, parameter2 : number){
	let options : Object =  {
      "observe" : 'body',
      "responseType": 'text'
    }
	url = this.url + "/" + parameter1 + "/" + parameter2;
	return this.http.put(url, options);
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
