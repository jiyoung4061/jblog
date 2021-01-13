package com.markany.jblog.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.markany.jblog.service.BlogService;
import com.markany.jblog.service.CategoryService;
import com.markany.jblog.vo.BlogVo;
import com.markany.jblog.vo.CategoryVo;
import com.markany.jblog.vo.UserVo;
import com.markany.security.Auth;
import com.markany.security.AuthUser;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {

	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CategoryService categoryService;

	@RequestMapping({ "", "/{category}", "/{category}/{post}" })
	public String index(@PathVariable String id, @PathVariable Optional<Long> category,
						@PathVariable Optional<Long> post, Model model) {
//		System.out.println(id + ":" + category + ":" + post);
		BlogVo blogVo = blogService.getBlog(id);
		List<CategoryVo> categoryVoList = categoryService.getCategoryList(id);
		model.addAttribute("blogVo", blogVo);
		model.addAttribute("categoryVoList",categoryVoList);
		return "blog/blog-main";
	}
	
	@Auth
	@RequestMapping(value="/blog/admin", method=RequestMethod.GET)
	public String admin(@PathVariable String id,@AuthUser UserVo authUser) {
		System.out.println("id>>>>"+id);
		System.out.println("auth>>>>"+authUser.getId());
		if(!id.equals(authUser.getId())) {
			return "redirect:/"+authUser.getId();
		}
		return "blog/blog-admin-basic";
	}
	
	@Auth
	@RequestMapping(value="/blog/admin", method=RequestMethod.POST)
	public String admin(@ModelAttribute @Valid BlogVo vo, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "blog/blog-admin-basic";
		}
		blogService.setMain(vo);
		model.addAttribute("blogVo", vo);
		return "redirect:/"+vo.getId()+"blog/blog-main";
	}
	
	@RequestMapping("/blog/admin/category")
	public String adminCategory(@PathVariable String id, Model model) {
		List<CategoryVo> categoryVoList = categoryService.getCategoryList(id);
//		List<Long> postCount = categoryService.getPostCount(id);
		model.addAttribute("categoryVoList",categoryVoList);
		return "blog/blog-admin-category";
	}
	
	@Auth
	@RequestMapping(value = "/category/add", method = RequestMethod.POST)
	public String addCategory(@PathVariable String id, @AuthUser UserVo authUser, 
								@ModelAttribute @Valid CategoryVo categoryVo, 
								BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "blog/blog-admin-category";
		}
		
		categoryVo.setId(id);
		categoryService.addCategory(categoryVo);
		model.addAttribute("authUser", authUser);
		return "redirect:/"+authUser.getId()+"/blog/admin/category";
	}
	
	@RequestMapping("/blog/admin/write")
	public String adminWrite() {
		
		return "blog/blog-admin-write";
	}
	@RequestMapping(value="/category/delete/{no}", method = RequestMethod.GET)
	public String deleteCategory(@PathVariable Long no, @AuthUser UserVo authUser) {
		System.out.println("no===>"+no);
		categoryService.deleteCategory(no);
		return "redirect:/"+authUser.getId()+"/blog/admin/category";
	}
}
