import { Component, OnInit } from '@angular/core';
import {TokenService} from "../Service/token.service";
import {Router} from "@angular/router";
import {JsonService} from "../Service/json.service";
import {Rol} from "../Model/rol";
import {LoginUsuario} from "../Model/loginUsuario";
import {Token} from "../Model/token";
import {enc, SHA256} from "crypto-js";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  isLoggedIn: boolean = false;
  isLoginFailed: boolean = false;
  loginUsuario: LoginUsuario;
  token: Token;

  constructor(private tokenService: TokenService, private router: Router, private json: JsonService) {
    this.loginUsuario = new LoginUsuario("", "");
    this.token = new Token("", "");
  }

  ngOnInit(): void {
    if (this.tokenService.getIdUsuario()) {
      this.isLoggedIn = true;
    }
  }

  encriptarPwd() {
    this.loginUsuario.hashPassword = SHA256(this.loginUsuario.hashPassword).toString(enc.Hex);
  }

  login() {
    this.encriptarPwd();
    this.json.login("user/login", this.loginUsuario).subscribe(
      result => {
        console.log(result);
        this.tokenService.setIdUsuario(result['idUsuario']);
        this.tokenService.setRol(result['rol']);
        this.isLoggedIn = true;
        this.router.navigate(['/home']);
      },
      error => {
        this.isLoginFailed = true;
      }
    );
  }
}
