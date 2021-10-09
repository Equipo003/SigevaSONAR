package com.isoEquipo3.PracticaEquipo3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
public class PracticaEquipo3Application {

	public static void main(String[] args) {
		SpringApplication.run(PracticaEquipo3Application.class, args);
	}

	@RequestMapping("/")
	@ResponseBody
	public String home() {
		return "Hola Mundo";
	}
	
	@RequestMapping("/bye")
	@ResponseBody
	public String bye() {
		return "Adiós Mundo";
	}
	
}
