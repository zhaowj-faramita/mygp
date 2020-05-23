package com.zhaowenjie.mygp.dao;

import com.zhaowenjie.mygp.bean.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IWordDao extends JpaRepository<Word,Integer> {
    Word queryById(int id);
    List<Word> queryWordByParentId(int parentId);
    List<Word> queryWordByWordLevel(int wordLevel);
    List<Word> queryWordByAuthor(String author);
    List<Word> queryWordByTitleLike(String title);
    @Query("select e from Word e ORDER BY e.publishDate desc ")
    List<Word> findAllOrderByPublishDate();
    void deleteByTitle(String title);
}
