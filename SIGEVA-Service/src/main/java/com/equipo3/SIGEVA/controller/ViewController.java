package com.equipo3.SIGEVA.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @RequestMapping({ "/home", "/crearCS", "/cnfgCupos"})
    public String index() {
        return "forward:/index.html";
    }
}
