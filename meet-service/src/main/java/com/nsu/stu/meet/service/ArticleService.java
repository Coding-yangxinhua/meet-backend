package com.nsu.stu.meet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.model.Article;
import com.nsu.stu.meet.model.dto.ArticleDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArticleService extends CheckService{

    /**
     * 发表文章
     * @param userId
     * @param article
     * @param files
     * @return
     */
    ResponseEntity<String> createArticle (Long userId, Article article, MultipartFile[] files);

    /**
     * 修改文章
     * @param userId
     * @param articleDto
     * @return
     */
    ResponseEntity<String> modifyArticle (Long userId, ArticleDto articleDto);

    /**
     * 批量删除文章
     * @param userId
     * @param articleIdList
     * @return
     */
    ResponseEntity<String> deleteArticleBatch (Long userId, List<Long> articleIdList);

    /**
     * 加载关注用户文章
     * @param userId
     * @param articleId
     * @param page
     * @param size
     * @return
     */
    ResponseEntity<IPage<ArticleDto>> selectArticleByFollow (Long userId, Long articleId, int page, int size);

    /**
     * 加载最新文章
     * @param userId
     * @param articleId
     * @param page
     * @param size
     * @return
     */
    ResponseEntity<IPage<ArticleDto>> selectArticleListLatest (Long userId, Long articleId, int page, int size);

    /**
     * 加载最热文章
     * @param userId
     * @param articleId
     * @param page
     * @param size
     * @return
     */
    ResponseEntity<IPage<ArticleDto>> selectArticleListHot(Long userId, Long articleId, int page, int size);

    /**
     * 加载指定用户文章
     * @param userId
     * @param queryId
     * @param articleId
     * @param page
     * @param size
     * @return
     */
    ResponseEntity<IPage<ArticleDto>> selectArticleByUserId (Long userId, Long queryId, Long articleId, int page, int size);


    /**
     * 加载用户浏览记录
     * @param userId
     * @param page
     * @param size
     * @return
     */
    ResponseEntity<IPage<ArticleDto>> selectArticleByHistory (Long userId, int page, int size);

    /**
     * 刷新用户关注的文章
     * @param userId
     * @param articleId
     * @param page
     * @param size
     * @return
     */
    ResponseEntity<IPage<ArticleDto>> refreshArticleByFollow (Long userId, Long articleId, int page, int size);

    /**
     * 刷新加载最新文章
     * @param userId
     * @param articleId
     * @param page
     * @param size
     * @return
     */
    ResponseEntity<IPage<ArticleDto>> refreshArticleListLatest (Long userId, Long articleId, int page, int size);

    /**
     * 刷新加载最热文章
     * @param userId
     * @param articleId
     * @param page
     * @param size
     * @return
     */
    ResponseEntity<IPage<ArticleDto>> refreshArticleListHot(Long userId, Long articleId, int page, int size);


    /**
     * 刷新指定用户文章
     * @param userId
     * @param queryId
     * @param articleId
     * @param page
     * @param size
     * @return
     */
    ResponseEntity<IPage<ArticleDto>> refreshArticleByUserId (Long userId, Long queryId, Long articleId, int page, int size);

    /**
     * 权限控制
     * @param articleId
     * @return
     */
    Long selectUserIdByArticle(Long articleId);

    /**
     * 通过id获取
     * @param articleId
     * @return
     */
    Article selectById(Long articleId);

    /**
     * 通过用户和文章id获取
     * @param userId
     * @param articleId
     * @return
     */
    ResponseEntity<ArticleDto> selectByArticleId(Long userId, Long articleId);

    /**
     * 测试
     * @return
     */
    List<Article> select();

}
