package io.github.libraryapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginViewController {

    @GetMapping("/login")
    public String paginaLogin(){
        return "login";
    }

//    @GetMapping("/")
//    @ResponseBody
//    public String paginaHome(Authentication authentication){
//        if(authentication instanceof CustomAuthentication customAuth){
//            System.out.println(customAuth.getUsuario());
//        }
//        return "Olá " + authentication.getName();
//    }

//    @GetMapping("/authorized")
//    @ResponseBody
//    public String getAuthorizationCode(@RequestParam("code") String code){
//        return "Seu authorization code: " + code;
//    }
}
