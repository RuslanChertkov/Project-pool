package com.example.pool.controllers;

import com.example.pool.model.Reservation;
import com.example.pool.model.User;
import com.example.pool.service.ReservationService;
import com.example.pool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class HomeController {

    private UserService userService;
    private ReservationService reservationService;

    public HomeController(UserService userService, ReservationService reservationService) {
        this.reservationService = reservationService;
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping(value = "/about")
    public String about(Model model) {
        model.addAttribute("currentUser", getUserData());
        return "about";
    }

    @GetMapping(value = "/403")
    public String accessDenied(Model model) {
        model.addAttribute("currentUser", getUserData());
        return "error/403";
    }

    @GetMapping(value = "/login")
    public String login(Model model) {
        model.addAttribute("currentUser", getUserData());
        return "login";
    }

    @GetMapping(value = "/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public ModelAndView addUser(@ModelAttribute User user, ModelAndView modelAndView) {
        User userFromDb = userService.findByEmail(user.getEmail());

        if (userFromDb != null) {
            modelAndView.addObject("message", "User exists!");
            modelAndView.setViewName("registration");
            return modelAndView;
        }
        userService.save(user);
        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }

    @GetMapping(value = "/profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView profile(ModelAndView modelAndView) {
        modelAndView.addObject("currentUser", getUserData());
        modelAndView.setViewName("profile");
        return modelAndView;
    }

    @GetMapping(value = "/reservation")
    public String reservation() {
        return "reservation";
    }

    @PostMapping(value = "/reservation")
    public ModelAndView reservationSend(@ModelAttribute Reservation reservation, ModelAndView modelAndView) {
        reservationService.save(reservation);
        modelAndView.setViewName("redirect:/index");
        return modelAndView;
    }

    private User getUserData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            org.springframework.security.core.userdetails.User secUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            User myUser = userService.findByEmail(secUser.getUsername());
            return myUser;
        }
        return null;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("reservation", new Reservation());
    }

}
