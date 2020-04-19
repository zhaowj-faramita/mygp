package com.zhaowenjie.mygp.service;

import com.zhaowenjie.mygp.bean.Word;
import com.zhaowenjie.mygp.exception.WordException;

import java.util.List;

public interface IWordService {

    Word addArticle(Word article) throws WordException;

    void removeArticle(int id) throws WordException;

    Word queryArticleById(int id) throws WordException;

    List<Word> findAll() throws WordException;

    List<Word> findArticleByParent_id(int category_id);
}
