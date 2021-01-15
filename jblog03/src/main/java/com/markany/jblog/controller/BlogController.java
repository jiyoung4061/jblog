package com.markany.jblog.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
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
import com.markany.jblog.service.PostService;
import com.markany.jblog.vo.BlogVo;
import com.markany.jblog.vo.CategoryVo;
import com.markany.jblog.vo.PostVo;
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

	@Autowired
	private PostService postService;

	@RequestMapping({ "", "/{category}", "/{category}/{post}" })
	public String index(@PathVariable String id, @PathVariable Optional<Long> category,
			@PathVariable Optional<Long> post, Model model, @AuthUser UserVo authUser) {
		
		BlogVo blogVo = blogService.getBlog(id);
		List<CategoryVo> categoryVoList = categoryService.getCategoryList(id);
		
		Long categoryNo = category.orElse(categoryService.getFirstCategory(id));
		List<PostVo> postVoList = postService.getPostList(categoryNo);

		Long postNo = post.orElse(postService.getFirstPost(categoryNo));
		PostVo postVo = postService.getPost(postNo);
		
		model.addAttribute("blogVo", blogVo);
		model.addAttribute("categoryVoList", categoryVoList);
		model.addAttribute("postVoList", postVoList);
		model.addAttribute("postVo", postVo);
		model.addAttribute("id", id);
		return "blog/blog-main";
	}

	@Auth
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin(@AuthUser UserVo authUser, @PathVariable String id, Model model) {
		if (!id.equals(authUser.getId())) {
			return "redirect:/" + id;
		}
		BlogVo vo = blogService.getBlog(id);
		model.addAttribute("blogVo", vo);
		return "blog/blog-admin-basic";
	}

	@Auth
	@RequestMapping(value = "/admin", method = RequestMethod.POST)
	public String admin(@ModelAttribute @Valid BlogVo vo, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "blog/blog-admin-basic";
		}
		blogService.setMain(vo);
		model.addAttribute("blogVo", vo);
		return "redirect:/" + vo.getId();
	}

	@Auth
	@RequestMapping("/admin/category")
	public String adminCategory(@PathVariable String id, Model model) {
		List<CategoryVo> categoryVoList = categoryService.getCategoryList(id);
//		List<Long> postCount = categoryService.getPostCount(id);
//		System.out.println("postcount"+postCount);
		BlogVo vo = blogService.getBlog(id);
		model.addAttribute("blogVo", vo);
		model.addAttribute("categoryVoList", categoryVoList);
		return "blog/blog-admin-category";
	}

	@Auth
	@RequestMapping(value = "admin/category/add", method = RequestMethod.POST)
	public String addCategory(@PathVariable String id, @AuthUser UserVo authUser,
			@ModelAttribute @Valid CategoryVo categoryVo, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "blog/blog-admin-category";
		}

		categoryVo.setId(id);
		categoryService.addCategory(categoryVo);
		model.addAttribute("authUser", authUser);
		return "redirect:/" + authUser.getId() + "/admin/category";
	}

	@Auth
	@RequestMapping(value = "admin/category/delete/{no}", method = RequestMethod.GET)
	public String deleteCategory(@PathVariable Long no, @AuthUser UserVo authUser) {
		categoryService.deleteCategory(no);
		return "redirect:/" + authUser.getId() + "/admin/category";
	}

	@Auth
	@RequestMapping(value = "admin/write", method = RequestMethod.GET)
	public String adminWrite(@PathVariable String id, Model model) {
		List<CategoryVo> categoryVoList = categoryService.getCategoryList(id);
		model.addAttribute("categoryVoList", categoryVoList);
		BlogVo vo = blogService.getBlog(id);
		model.addAttribute("blogVo", vo);
		return "blog/blog-admin-write";
	}

	@Auth
	@RequestMapping(value = "admin/write", method = RequestMethod.POST)
	public String adminWrite(@ModelAttribute @Valid PostVo postVo, @AuthUser UserVo authUser) {
		postService.addPost(postVo);
		return "redirect:/" + authUser.getId();
	}
}
