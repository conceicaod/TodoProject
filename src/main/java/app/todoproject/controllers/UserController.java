package app.todoproject.controllers;

import app.todoproject.entity.User;
import app.todoproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

   /* @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/

    @RequestMapping("/register")
    public String showRegistrationPage(){
        return "registration-form";
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/registerUsers")
    public String registerUser(@RequestParam("username")String someUsername,
                               @RequestParam("password") String somePassword,
                                Model model){

        User user = userService.retrieveUserByUsername(someUsername);
        if(user==null){
            userService.saveUser(someUsername, passwordEncoder.encode(somePassword));
            return "redirect:/login";
        }else{
            model.addAttribute("errorMessage", "Duplicate username!");
            return "error";
        }


    }

}
