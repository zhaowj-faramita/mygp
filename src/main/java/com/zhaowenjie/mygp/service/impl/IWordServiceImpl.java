package com.zhaowenjie.mygp.service.impl;

import com.zhaowenjie.mygp.bean.Word;
import com.zhaowenjie.mygp.dao.IWordDao;
import com.zhaowenjie.mygp.exception.WordException;
import com.zhaowenjie.mygp.service.IWordService;
import com.zhaowenjie.mygp.utils.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<Word> authorWord =iWordDao.queryWordByAuthor(author);
        authorWord.sort((o1,o2)->{
            return o1.getWordLevel()>o2.getWordLevel()?-1:1;
        });
        return authorWord;
    }

    @Override
    public List<Word> randomQuery(int number) {
        List<Word> allWord = this.findAll();
        List<Word> randomWord = new ArrayList<>(0);
        List<Integer> randomIndex = new ArrayList<>();
        for(int i = 0;i<number;i++){
            Integer index = (int)(Math.random()*(allWord.size()));
            if(!randomIndex.contains(index)){
                randomIndex.add(index);
                randomWord.add(allWord.get(index));
            }else{
                i--;
            }
        }
        randomWord.sort((o1,o2)->{
           return o1.getPublishDate().getTime()>o2.getPublishDate().getTime()?1:-1;
        });
        return randomWord;
    }

    @Override
    public List<Word> queryWordByTitle(String title) {
        return iWordDao.queryWordByTitleLike(title);
    }
}
