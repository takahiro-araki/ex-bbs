package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.form.ArticleForm;
import com.example.repository.ArticlerRepository;

/**
 * 記事コントローラー.
 * 
 * @author takahiro.araki
 *
 */
@Controller
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticlerRepository articlerRepository;

	/**
	 * 記事全件表示画面を表示する.
	 * 
	 * @param リクエストスコープmodel
	 * @return 記事情報の入ったリスト
	 */
	@RequestMapping("")
	public String index(Model model) {
		List<Article> articleList = articlerRepository.findAll();
		model.addAttribute("articleList", articleList);
		return "article";
	}

	/**
	 * @return
	 */
	@ModelAttribute
	public ArticleForm setupForm() {
		return new ArticleForm();
	}

	/**
	 * 新規で入力された記事を追加し、記事全件表示画面を表示する
	 * 
	 * @param 新規入力された記事情報のフォーム
	 * @return 記事全件表示へリターン
	 */
	@RequestMapping("/input")
	public String insertArticle(ArticleForm articleForm) {
		Article article = new Article();
		article.setName(articleForm.getName());
		article.setContent(articleForm.getContent());
		articlerRepository.insert(article);
		return "redirect:";
	}

}
