package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Comment;

/**
 * コメントレポジトリ
 * @author takahiro.araki
 *
 */
@Repository
public class CommentRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Comment> COMMENT_ROW_MAPPER = (rs, i) -> {
		Comment comment = new Comment();
		comment.setId(rs.getInt("id"));
		comment.setName(rs.getString("name"));
		comment.setContent(rs.getString("content"));
		comment.setArticleId(rs.getInt("article_id"));
		return comment;
	};

	/**
	 * 記事IDと紐づくコメント情報を抽出する.
	 * 
	 * @param 記事ID
	 * @return コメント情報の入ったリスト
	 */
	public List<Comment> findByArticleId(int articleId) {
		String findByArticleIdSql = "SELECT  id,name,content,article_id FROM comments WHERE article_id=:articleId ORDER BY id desc";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		List<Comment> commentList = template.query(findByArticleIdSql, param, COMMENT_ROW_MAPPER);
		return commentList;
	}

	/**
	 * 新規コメントを挿入．
	 * @param コメントドメイン
	 */
	public void insert(Comment comment) {
		String insertSql = "INSERT INTO comments(name,content,article_id) VALUES(:name,:content,:articleId)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
		template.update(insertSql, param);
	}
	
	/**
	 * 記事IDで指定したレコードを削除．
	 * @param 記事ID
	 */
	public void deleteByArticleId(int articleId) {
		String deleteSql="DELETE FROM comments WHERE article_id=:articleId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		template.update(deleteSql, param);
	}
}
