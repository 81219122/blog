package com.zhangrun.controller.admin;

import com.zhangrun.entity.Tag;
import com.zhangrun.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author zhangrun
 * @version 1.0
 * @date 2020/5/4 10:00
 */
@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private ITagService service;
    @GetMapping("/tags")  //分页 默认十条数据 根据id排序 默认倒序
    public String types(@PageableDefault(size = 6,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page",service.listTag(pageable));
        return "admin/tags";
    }
    /*返回打牌一个新增页面*/
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    @PostMapping("/tags")
    public String save(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag typeByName = service.findByName(tag.getName());
        if (typeByName!=null){//分类名称重复
            result.rejectValue("name","nameError","分类名称重复");
        }
        if (result.hasErrors()){ //如果参数有误就返回到新增页面
            return "admin/tags-input";
        }
        Tag save = service.save(tag);
        if (save==null){ //没保存成功
            attributes.addFlashAttribute("message","新增失败");
        }else { //保存成功
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/tags";
    }

    /*根据id查询type对象，作用数据回显*/
    @GetMapping("/tags/{id}/input")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("tag",service.getTag(id));
        return "admin/tags-input";
    }

    /*更新保存功能*/
    @PostMapping("/tags/{id}")
    public String update(@Valid Tag tag, BindingResult result,@PathVariable Long id, RedirectAttributes attributes){
        Tag typeByName = service.findByName(tag.getName());
        if (typeByName!=null){//分类名称重复
            result.rejectValue("name","nameError","标签名称重复");
        }
        if (result.hasErrors()){ //如果参数有误就返回到新增页面
            return "admin/tags-input";
        }
        Tag save = service.updateTag(id,tag);
        if (save==null){ //没保存成功
            attributes.addFlashAttribute("message","更新失败");
        }else { //保存成功
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        service.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }
}
