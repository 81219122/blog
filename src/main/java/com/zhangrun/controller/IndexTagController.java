package com.zhangrun.controller;

import com.zhangrun.entity.Tag;
import com.zhangrun.service.IBlogService;
import com.zhangrun.service.ITagService;
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
 * @date 2020/5/7 11:11
 */
@Controller
public class IndexTagController {
    @Autowired
    private ITagService tagService;
    @Autowired
    private IBlogService blogService;

    /*
     * @Param [] 标签对象的id
     * @return java.lang.String
     * 返回到根据标签显示博客列表
    */
    @GetMapping("/tags/{id}")
    public String getTagsAndBlog(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable
            , @PathVariable Long id, Model model){
        List<Tag> tags = tagService.listTagTop(10000);

        if (id==-1){
            id=tags.get(0).getId();
        }
        model.addAttribute("tags",tags);
        model.addAttribute("page",blogService.findByTagId(id,pageable));
        model.addAttribute("activeTagId",id);//给前端做样式区分
        return "tags";
    }
}
