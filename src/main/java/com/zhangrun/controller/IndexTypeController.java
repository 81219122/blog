package com.zhangrun.controller;

import com.zhangrun.entity.Type;
import com.zhangrun.service.IBlogService;
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

import java.util.List;

/**
 * @author zhangrun
 * @version 1.0
 * @date 2020/5/7 10:18
 */
@Controller
public class IndexTypeController {
    @Autowired
    private ITypeService typeService;
    @Autowired
    private IBlogService blogService;

    /*
     * @Param []type的id
     * @return java.lang.String
     * 去前端分类展示博客页面
    */
    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable
                        , @PathVariable Long id, Model model){
        List<Type> types = typeService.listTypeTop(10000);
        if (id==-1){//页面初始化值-1，
            id=types.get(0).getId(); //拿分类的第一个id
        }
        BlogQuery blogQuery=new BlogQuery(); //根据分类id查 把分类id封装到该对象
        blogQuery.setTypeId(id);
        model.addAttribute("types",types);
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        model.addAttribute("activeTypeId",id);//给前端做样式区分
        return "/types";
    }



}
