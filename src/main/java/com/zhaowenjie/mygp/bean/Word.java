package com.zhaowenjie.mygp.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "mygp_word")
@ApiModel
public class Word implements Serializable {

    private static final long serialVersionUID = 3777076426188359573L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "文档的id",required = true)
    int id;
    @ApiModelProperty(value = "题目",required = true)
    String title;
    @ApiModelProperty(value = "内容",required = true)
    String textArea;
    @ApiModelProperty(value = "作者",required = true)
    String Author;
    @ApiModelProperty(value = "上传时间",required = true)
    Date publishDate;
    @ApiModelProperty(value = "文档等级",hidden = true)
    int parent_id;

    public Word() {
    }

    public Word(String title, String textArea, String author, Date publishDate, int parent_id) {
        this.title = title;
        this.textArea = textArea;
        Author = author;
        this.publishDate = publishDate;
        this.parent_id = parent_id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTextArea() {
        return textArea;
    }

    public void setTextArea(String textArea) {
        this.textArea = textArea;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }
}
