package com.zhangrun.controller;

import com.zhangrun.entity.Comment;
import com.zhangrun.entity.User;
import com.zhangrun.service.IBlogService;
import com.zhangrun.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * @author zhangrun
 * @version 1.0
 * @date 2020/5/6 14:10
 */
@Controller
public class CommentController {
    @Autowired
    private ICommentService service;

    @Autowired
    private IBlogService blogService;

    @Value("${comment.avatar}")  //取配置文件的值
    private String avatar;

    /*评论功能，返回一个片段*/
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments",service.listCommentByBlogId(blogId));
        return "blog ::commentList"; //返回一个片段
    }

    /*发表评论接收功能*/
    @PostMapping("/comments")
    public String saveComment(Comment comment, HttpSession session){
        User user = (User)session.getAttribute("user");
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        if (user==null){
            comment.setAvatar(avatar);
        }else {
            comment.setAdminComment(true);
            //comment.setNickname(user.getNickname());
            comment.setAvatar(user.getAvatar());
        }
        service.saveComment(comment);
        return "redirect:/comments/"+blogId;
    }
}
