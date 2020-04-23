package com.zhaowenjie.mygp.service.impl;

import com.zhaowenjie.mygp.bean.Word;
import com.zhaowenjie.mygp.dao.IWordDao;
import com.zhaowenjie.mygp.exception.WordException;
import com.zhaowenjie.mygp.service.IWordService;
import com.zhaowenjie.mygp.utils.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class IWordServiceImpl implements IWordService {

    @Autowired
    IWordDao iWordDao;

    @Override
    public Word addWord(Word word) throws WordException {
        if (word == null) {
            throw new WordException(CodeUtil.DEADLY_CODE, "addArticle:参数为空");
        } else {
            word.setPublishDate(new Date());
            return iWordDao.save(word);
        }
    }

    @Override
    public void deleteWord(int id) throws WordException {
        iWordDao.deleteById(id);
    }

    @Override
    public void deleteWordByTitle(String title) throws WordException {
        iWordDao.deleteByTitle(title);
    }

    @Override
    public Word queryWordById(int id) throws WordException {
        return iWordDao.queryById(id);
    }

    @Override
    public List<Word> findAll() throws WordException {
        return iWordDao.findAll();
    }

    @Override
    public List<Word> findAllOrderByPublishDate() throws WordException {
        return iWordDao.findAllOrderByPublishDate();
    }

    @Override
    public List<Word> queryWordByParent_id(int parent_id) {
        return iWordDao.queryWordByParentId(parent_id);
    }

    @Override
    public List<Word> queryWordByWordLevel(int wordLevel) {
        return iWordDao.queryWordByWordLevel(wordLevel);
    }

    @Override
    public List<Word> queryWordByAuthor(String author) {
        return iWordDao.queryWordByAuthor(author);
    }
}
