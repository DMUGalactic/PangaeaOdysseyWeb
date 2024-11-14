package com.PangaeaOdyssey.PangaeaOdyssey.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "index.html"; // index.html로 포워딩
    }
    @GetMapping("/home")
    public String homeP() {
        System.out.println("helloworld");
        System.out.println("컨피그 테스트");
        return "home.html";  // home.html 페이지로 이동 (src/main/resources/templates/home.html)
    }
}
