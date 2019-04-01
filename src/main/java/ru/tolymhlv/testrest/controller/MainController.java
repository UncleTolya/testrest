package ru.tolymhlv.testrest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.tolymhlv.testrest.domain.User;
import ru.tolymhlv.testrest.domain.Visit;
import ru.tolymhlv.testrest.repo.VisitRepo;
import ru.tolymhlv.testrest.service.UserService;
import ru.tolymhlv.testrest.service.VisitService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class MainController {

    private final UserService userService;
    private final VisitService visitService;

    @Autowired
    public MainController(UserService userService, VisitService visitService) {
        this.userService = userService;
        this.visitService = visitService;
    }

    @RequestMapping("/**")
    public String main(final Model model,
                       final HttpServletRequest request) {
        final String ip = request.getRemoteAddr();
        final String userAgent = request.getHeader("user-agent");
        final User user = userService.getUser(ip, userAgent);

        final String url = request.getRequestURI();
        final String queryString = request.getQueryString();
        final String fullUrl = url + queryString;
        final Visit visit = visitService.addVisit(user, fullUrl, new Date());

//        final String host = request.getRemoteHost();
//        List<String> params = new ArrayList<>();
//        params.add("ip = " + ip);
//        params.add("host = " + host);
//        params.add("userAgent = " + userAgent);
//        params.add("url = " + url);
//        params.add("query = " + queryString);
//        model.addAttribute("params", params);

        return "main";
    }


    @PostMapping
    public String main(final @RequestParam(required = false, defaultValue = "today") String date,
                       final Model model,
                       final HttpServletRequest request) {

        return "main";
    }


}
