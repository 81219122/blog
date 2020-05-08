package com.zhangrun.service.impl;

import com.zhangrun.dao.IBlogDao;
import com.zhangrun.entity.Blog;
import com.zhangrun.entity.Type;
import com.zhangrun.exception.NotFoundException;
import com.zhangrun.service.IBlogService;
import com.zhangrun.utils.MarkDownUtil;
import com.zhangrun.utils.MyBeanUtils;
import com.zhangrun.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @author zhangrun
 * @version 1.0
 * @date 2020/5/4 10:22
 */
@Service
public class BlogServiceImpl implements IBlogService {
    @Autowired
    private IBlogDao dao;
    @Override
    public Blog getBlog(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return dao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate>list=new ArrayList<>();
                if (blog.getTitle()!=null&& !"".equals(blog.getTitle())){
                    list.add(cb.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
                }
                if (blog.getTypeId()!=null){
                    //get拿到一个type对象 前面拿数据库字段后面拿值
                    list.add(cb.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }
                if (blog.isRecommend()){
                    list.add(cb.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
                cq.where(list.toArray(new Predicate[list.size()])); //转换成数组 然后给他设置长度
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return dao.findByPublished(pageable);
    }


    @Transactional
    @Override
    public Blog save(Blog blog) {
        if (blog.getId()==null){//如果id为空就是新增否则是编辑
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0); //初始化浏览数量
            blog.setFlag("原创");
        }else {//这是编辑
            blog.setUpdateTime(new Date());
        }


        return dao.save(blog);
    }
    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog blog1 = dao.findById(id).get();
        if (blog1==null){
            throw new NotFoundException("该博客对象不存在");
        }
        //把页面传过来的参数对象跟数据库查到的对象复制然后保存
        BeanUtils.copyProperties(blog,blog1, MyBeanUtils.getNullPropertyNames(blog));  //过滤掉空值
        blog1.setUpdateTime(new Date());
        return dao.save(blog1);
    }
    @Transactional
    @Override
    public void deleteBlog(Long id) {
        dao.deleteById(id);
    }

    @Override
    public List<Blog> listBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable= PageRequest.of(0,size,sort);
        return dao.findTop(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return dao.findByQuery(query,pageable);
    }

    @Override
    public Blog getAndHtml(Long id) {
        Blog blog = dao.findById(id).get();
        if (blog ==null){
            throw new NotFoundException("该博客不存在");
        }else {//处理博客的内容 这样重新构建一个blog对象是为了避免你处理完博客的内容然后会把html元素保存到数据库
            Blog b=new Blog();
            BeanUtils.copyProperties(blog,b);  //把数据库查询出来的blog对象复制给我新创建的blog对象
            String content=b.getContent();
            String s = MarkDownUtil.markdownToHtmlExtensions(content);
            b.setContent(s);
            dao.updateViews(id); //更新浏览次数
            return b;
        }
    }

    @Override
    public Page<Blog> findByTagId(Long id,Pageable pageable) {
        return dao.findAll(new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery cq, CriteriaBuilder cb) {
                Join join=root.join("tags");
                return cb.equal(join.get("id"),id);
            }
        },pageable);
    }


    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years=dao.findGroupYear();
        Map<String,List<Blog>> map=new LinkedHashMap<>();
        for (String y:years){
            map.put(y,dao.findByYear(y));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return dao.count();
    }
}
