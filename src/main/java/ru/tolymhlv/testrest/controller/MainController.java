package ru.tolymhlv.testrest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    @GetMapping
    public String main(final Model model, final HttpServletRequest request) {

        final String ip = request.getRequestURI();
        final String userAgent = request.getHeader("user-agent");

        return "main";
    }
}
