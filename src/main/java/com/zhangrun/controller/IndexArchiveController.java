package com.zhangrun.controller;

import com.zhangrun.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zhangrun
 * @version 1.0
 * @date 2020/5/7 12:06
 * 归档控制层类
 */
@Controller
public class IndexArchiveController{
    @Autowired
    private IBlogService blogService;

    @GetMapping("/archives")
    public String archive(Model model) {
        model.addAttribute("archiveMap",blogService.archiveBlog());
        model.addAttribute("blogCount",blogService.countBlog());
        return "archives";
    }
}
