package com.zhaowenjie.mygp.service.impl;

import com.zhaowenjie.mygp.bean.Word;
import com.zhaowenjie.mygp.dao.IWordDao;
import com.zhaowenjie.mygp.exception.WordException;
import com.zhaowenjie.mygp.service.IWordService;
import com.zhaowenjie.mygp.utils.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class IWordServiceImpl implements IWordService {

    @Autowired
    IWordDao iWordDao;

    @Override
    public Word addWord(Word word) throws WordException {
        if (word == null) {
            throw new WordException(CodeUtil.DEADLY_CODE, "addArticle:参数为空");
        } else {
            return iWordDao.save(word);
        }
    }

    @Override
    public void removeWord(int id) throws WordException {
        iWordDao.deleteById(id);
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
    public List<Word> findWordByParent_id(int parent_id) {
        return iWordDao.queryByParent_id(parent_id);
    }
}
