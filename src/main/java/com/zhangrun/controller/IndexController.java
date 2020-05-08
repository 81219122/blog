package com.zhangrun.controller;

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
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangrun
 * @version 1.0
 * @date 2020/5/5 13:33
 * 博客展示首页
 */
@Controller
public class IndexController {
    @Autowired
    private IBlogService service;

    @Autowired
    private ITypeService typeService;
    @Autowired
    private ITagService tagService;
    @RequestMapping("/")
    public String index(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         Model model){
        model.addAttribute("page",service.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));//前端查询六条数据
        model.addAttribute("tags",tagService.listTagTop(10));//前端查询十条数据
        model.addAttribute("recommendBlogs",service.listBlogTop(8));
        return "index";
    }

    /*博客首页查询功能*/
    @PostMapping("/search")
    public String search(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model){
        model.addAttribute("page",service.listBlog(query,pageable));
        model.addAttribute("query",query); //搜索条件回显
        return "search";
    }

    /*查询单个博客详情功能*/
    @GetMapping("/blog/{id}")
    public String findOneBlog(@PathVariable Long id,Model model) {
        model.addAttribute("blog",service.getAndHtml(id));
        return "blog";
    }

    /*
     * @Param []
     * @return java.lang.String
     * 首页的footer最新博客推荐
    */
    @GetMapping("/footer/newBlog")
    public String  newBlogs(Model model){
        model.addAttribute("newBlogs",service.listBlogTop(3));
        return "init::newBlogList";
    }
}
