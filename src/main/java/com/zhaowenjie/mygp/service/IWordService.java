package com.zhaowenjie.mygp.service;

import com.zhaowenjie.mygp.bean.Word;
import com.zhaowenjie.mygp.exception.WordException;

import java.util.List;

public interface IWordService {

    Word addWord(Word word) throws WordException;

    void removeWord(int id) throws WordException;

    Word queryWordById(int id) throws WordException;

    List<Word> findAll() throws WordException;

    List<Word> findWordByParent_id(int parent_id);
}
