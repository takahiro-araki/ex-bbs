package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.repository.ArticlerRepository;
import com.example.repository.CommentRepository;

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

	@Autowired
	private CommentRepository commentRepository;

	/**
	 * 記事リクエストパラメーターを格納する記事フォームをリターンする.
	 * 
	 * @return
	 */
	@ModelAttribute
	public ArticleForm setupArticleForm() {
		return new ArticleForm();
	}
	
	/**
	 * コメントリクエストパラメーターを格納するコメントフォームをリターンする.
	 * @return
	 */
	@ModelAttribute
	public CommentForm setupCommentForm() {
		return new CommentForm();
	}

	/**
	 * 記事・コメント全件表示画面を表示する
	 * 
	 * @param リクエストスコープmodel
	 * @return 記事情報の入ったリスト
	 */
	@RequestMapping("")
	public String index(Model model) {
		List<Article> articleList = articlerRepository.findAll();
		List<Comment> commentList = new ArrayList<>();
		for (Article article : articleList) {
			commentList = commentRepository.findByArticleId(article.getId());
			article.setCommentList(commentList);
		}
		
		model.addAttribute("articleList", articleList);
		return "article";
	}


	/**
	 * 新規で入力された記事を追加し、記事全件表示画面を表示する.
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
	
	/**
	 * 投稿したコメントを画面に表示する．
	 * @param リクエストパラメータを受け取ったフォーム
	 * @return　記事・コメント全表示画面
	 */
	@RequestMapping("/inputComment")
	public String insertComment(CommentForm commentForm) {
		Comment comment=new Comment();
		int intArticleId=Integer.parseInt(commentForm.getArticleId());
		comment.setName(commentForm.getName());
		comment.setContent(commentForm.getContent());
		comment.setArticleId(intArticleId);
		commentRepository.insert(comment);
		return "redirect:";
	}
	
	/**
	 * 記事idに紐づいた記事とコメントを削除.
	 * @param リクエストパラメータ記事id
	 * @return　記事・コメント全件表示画面を表示
	 */
	@RequestMapping("/delete")
	public String deleteArticle(Integer id) {
		commentRepository.deleteByArticleId(id);
		articlerRepository.deleateById(id);
		return "redirect:";
	}

}
