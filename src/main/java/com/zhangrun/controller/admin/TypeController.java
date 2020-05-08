package com.zhangrun.controller.admin;

import com.zhangrun.entity.Type;
import com.zhangrun.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
 * @date 2020/5/2 10:02
 */
@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private ITypeService typeService;
    @GetMapping("/types")  //分页 默认十条数据 根据id排序 默认倒序
    public String types(@PageableDefault(size = 6,sort = {"id"},direction = Sort.Direction.DESC)
                                Pageable pageable, Model model) {
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }
    /*返回打牌一个新增页面*/
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    @PostMapping("/types")
    public String save(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        Type typeByName = typeService.getTypeByName(type.getName());
        if (typeByName!=null){//分类名称重复
            result.rejectValue("name","nameError","分类名称重复");
        }
        if (result.hasErrors()){ //如果参数有误就返回到新增页面
            return "admin/types-input";
        }
        Type save = typeService.save(type);
        if (save==null){ //没保存成功
            attributes.addFlashAttribute("message","新增失败");
        } else { //保存成功
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/types";
    }

    /*根据id查询type对象，作用数据回显*/
    @GetMapping("/types/{id}/input")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }

    /*更新保存功能*/
    @PostMapping("/types/{id}")
    public String update(@Valid Type type, BindingResult result,@PathVariable Long id, RedirectAttributes attributes){
        Type typeByName = typeService.getTypeByName(type.getName());
        if (typeByName!=null){//分类名称重复
            result.rejectValue("name","nameError","分类名称重复");
        }
        if (result.hasErrors()){ //如果参数有误就返回到新增页面
            return "admin/types-input";
        }
        Type save = typeService.updateType(id,type);
        if (save==null){ //没保存成功
            attributes.addFlashAttribute("message","更新失败");
        }else{ //保存成功
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }
}
