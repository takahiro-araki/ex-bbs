package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;

/**
 * 記事レポジトリ
 * 
 * @author takahiro.araki
 *
 */
@Repository
public class ArticlerRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) -> {
		Article article = new Article();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));
		return article;
	};

	/**
	 * 記事情報を全件検索して、リターンする.
	 * 
	 * @return 全件検索した記事情報が入ったリスト
	 */
	public List<Article> findAll() {
		String indexSql = "SELECT id,name,content FROM articles ORDER BY id desc";
		List<Article> articleList = template.query(indexSql, ARTICLE_ROW_MAPPER);
		return articleList;
	}

	/**
	 *記事情報を挿入する.
	 * 
	 * @param 記事情報
	 */
	public void insert(Article article) {
		String insertSql = "INSERT INTO articles (name,content) VALUES(:name,:content)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", article.getName()).addValue("content",
				article.getContent());
		template.update(insertSql, param);
	}

}
