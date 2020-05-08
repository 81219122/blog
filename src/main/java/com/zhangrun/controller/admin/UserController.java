package com.zhangrun.controller.admin;

import com.zhangrun.entity.User;
import com.zhangrun.service.IUserService;
import com.zhangrun.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author zhangrun
 * @version 1.0
 * @date 2020/4/30 12:51
 */

@Controller
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private IUserService userService;
    @GetMapping()
    public String loginPage(){
        return "admin/login";
    }

    @PostMapping("/login")  //post请求用@RequestParam 注解拿参数
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpSession session,RedirectAttributes attributes){
        User user = userService.checkUser(username, password);
        if (user!=null){
            user.setPassword(null); //不把密码传到session
            session.setAttribute("user",user);
            return "admin/index";
        }else {
            //因为是重定向 所以用model传信息的话页面拿不到
            attributes.addFlashAttribute("message","用户名或密码错误");
            return "redirect:/admin"; //使用重定向  使用前面的方法
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
