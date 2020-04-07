package com.numericalanalysis.numericalalanalysisbackend.controllers;

import com.google.gson.Gson;
import com.numericalanalysis.numericalalanalysisbackend.model.Comment;
import com.numericalanalysis.numericalalanalysisbackend.model.Origin;
import com.numericalanalysis.numericalalanalysisbackend.model.User;
import com.numericalanalysis.numericalalanalysisbackend.services.CommentServiceImpl;
import com.numericalanalysis.numericalalanalysisbackend.services.PasswordDropping;
import com.numericalanalysis.numericalalanalysisbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Date;

@RestController
@RequestMapping("/api")
public class InformationController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/get_user_data"},method = RequestMethod.GET)
    public String getUserData(Model m, Principal principal) {
        if(principal == null)
            return "{\"error\": \"You are not authorized\"}";
        return userService.findByEmail( principal.getName() ).toJSON();
    }

    @RequestMapping(value = {"/get_user_name"},method = RequestMethod.GET)
    public String getUserName(Model m, Principal principal) {
        if(principal == null)
            return "{\"error\": \"You are not authorized\"}";
        return "{ \"user\": \"" + principal.getName() + "\"}";
    }


    @RequestMapping(value = {"/edit_user_data"},method = RequestMethod.POST)
    public void editUserData(HttpServletResponse response, User u, @RequestParam("file") MultipartFile file, String newPassword, Model m, Principal principal) throws Exception {//TODO: try/catch
        //BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println( u );
        System.out.println( newPassword );
        u.setPhoto(file.getBytes());
        userService.edit( principal.getName(), newPassword, u );
        response.sendRedirect(u.getEmail().equals(principal.getName()) ? "/" : "/logout");
        //return "{\"error\": \"You are not authorized\"}";
    }

    @RequestMapping(value="/reply_comment", method=RequestMethod.POST)
    public String replyComment(String origin,String comment, int id, Model model, Principal principal) {
        System.out.println(origin);
        if(principal == null)
            //response.sendRedirect("/login");
            return "{\"error\": \"You are not authorised\"";
        else {
            commentService.addComment(new Comment(new Date(), userService.findByEmail(principal.getName()), comment, Origin.valueOf(origin)), id);
            //response.sendRedirect(req.getRequestURI());
            return "{}";
        }
    }

    @RequestMapping(value = "/add_comment", method = RequestMethod.POST)
    public String addComment(String origin,String comment,Model model,Principal principal) {
        System.out.println(origin);
        if(principal == null)
            //response.sendRedirect("/login");
            return "{\"error\": \"You are not authorised\"";
        else {
            commentService.addComment(new Comment(new Date(), userService.findByEmail(principal.getName()), comment, Origin.valueOf(origin)));
            //response.sendRedirect(req.getRequestURI());
            return "{}";
        }
    }

    @Autowired
    @Qualifier("singleInst")
    private CommentServiceImpl commentService;// = CommentService.getInstance();
    private final Gson gson = new Gson();

    @RequestMapping("/drop_password")
    public String dropPassword(String email, Model model) {
        try{
            passwordDropping.dropPassword(email);
            return "Password has been successfully dropped";
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
    }

    @Autowired
    private PasswordDropping passwordDropping;
    @ResponseBody
    @RequestMapping("/get_photo")
    public byte[] getPhoto(String email, Model model) {
        return userService.findByEmail(email).getPhoto();
    }


}