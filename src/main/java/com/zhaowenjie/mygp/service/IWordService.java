package com.zhaowenjie.mygp.service;

import com.zhaowenjie.mygp.bean.Word;
import com.zhaowenjie.mygp.exception.WordException;

import java.util.List;

public interface IWordService {

    Word addWord(Word word) throws WordException;

    void deleteWord(int id) throws WordException;

    void deleteWordByTitle(String title) throws WordException;

    Word queryWordById(int id) throws WordException;

    List<Word> findAll() throws WordException;

    List<Word> findAllOrderByPublishDate()throws WordException;

    List<Word> queryWordByParent_id(int parent_id);

    List<Word> queryWordByWordLevel(int wordLevel);

    List<Word> queryWordByAuthor(String author);

    List<Word> randomQuery(int number);
}
