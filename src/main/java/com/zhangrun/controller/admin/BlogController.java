package com.zhangrun.controller.admin;

import com.zhangrun.entity.Blog;
import com.zhangrun.entity.User;
import com.zhangrun.service.IBlogService;
import com.zhangrun.service.ITagService;
import com.zhangrun.service.ITypeService;
import com.zhangrun.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author zhangrun
 * @version 1.0
 * @date 2020/4/30 13:19
 */
@Controller
@RequestMapping("/admin")
public class BlogController {
    private static final String INPUT="admin/blogs-input";
    private static final String LIST="admin/blogs";
    private static final String REDIRECT_LIST="redirect:/admin/blogs";

    @Autowired
    private IBlogService service;
    @Autowired
    private ITypeService typeService;
    @Autowired
    private ITagService tagService;

    /*返回到博客列表页面*/
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model){
        model.addAttribute("types",typeService.listType()); //查询所有博客分类
        model.addAttribute("page",service.listBlog(pageable,blog));
        return LIST;
    }

    /*博客查询*/
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page",service.listBlog(pageable,blog));
        return "admin/blogs ::blogList";  //返回一个片段，只更新博客表格内容
    }

    /*返回到博客新增页面*/
    @GetMapping("/blogs/input")
    public String input(Model model){

        model.addAttribute("blog",new Blog());
        model.addAttribute("types",typeService.listType()); //查询所有博客分类
        model.addAttribute("tags",tagService.listTag()); //查询所有标签
        Blog b=new Blog();

        return INPUT;
    }

    /*新增和编辑功能合并*/
    @PostMapping("/blogs")
    public  String saveAndEdit(Blog blog, RedirectAttributes attributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));  //写入分类
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if (blog.getId()==null){
            b=service.save(blog);
        }else {
            b=service.updateBlog(blog.getId(),blog);
        }

        if (b==null){
            attributes.addFlashAttribute("message","操作失败");
        }else {
            attributes.addFlashAttribute("message","操作成功");
        }
        return REDIRECT_LIST;
    }


    /*编辑功能跳转页面并携带blog对象实现数据回显*/
    @GetMapping("/blogs/{id}/input")
    public String editBtn(@PathVariable Long id, Model model){
        model.addAttribute("types",typeService.listType()); //查询所有博客分类
        model.addAttribute("tags",tagService.listTag()); //查询所有标签
        Blog blog = service.getBlog(id);
        blog.init(); //初始化，目的 把tags集合处理成一个字符串1,2,3这种形式，方便页面数据回显
        model.addAttribute("blog",blog); //根据id查询blog对象
        return INPUT;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        service.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return REDIRECT_LIST;
    }
}
