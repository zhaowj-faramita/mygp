package com.zhaowenjie.mygp.dao;

import com.zhaowenjie.mygp.bean.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IWordDao extends JpaRepository<Word,Integer> {
    Word queryById(int id);
    List<Word> queryByParent_id(int category_id);
}
