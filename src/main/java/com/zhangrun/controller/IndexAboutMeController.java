package com.zhangrun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zhangrun
 * @version 1.0
 * @date 2020/5/7 12:41
 */
@Controller
public class IndexAboutMeController {

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
