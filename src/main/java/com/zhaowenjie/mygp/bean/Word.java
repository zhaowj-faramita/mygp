package com.zhaowenjie.mygp.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "mygp_word")
@ApiModel
public class Word implements Serializable {

    private static final long serialVersionUID = 3777076426188359573L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "文档的id")
    private int id;
    @ApiModelProperty(value = "题目",required = true)
    private String title;
    @ApiModelProperty(value = "内容",required = true)
    private String textArea;
    @ApiModelProperty(value = "作者",required = true)
    private String author;
    @ApiModelProperty(value = "上传时间",hidden = true)
    private Date publishDate;
    @ApiModelProperty(value = "文档从属")
    private int parentId;
    @ApiModelProperty(value = "文档等级",required = true)
    private int wordLevel;

    public Word() {
    }

    public Word(String title, String textArea, String author, Date publishDate, int parentId, int wordLevel) {
        this.title = title;
        this.textArea = textArea;
        this.author = author;
        this.publishDate = publishDate;
        this.parentId = parentId;
        this.wordLevel = wordLevel;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", textArea='" + textArea + '\'' +
                ", author='" + author + '\'' +
                ", publishDate=" + publishDate +
                ", parentId=" + parentId +
                ", wordLevel=" + wordLevel +
                '}';
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
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getWordLevel() {
        return wordLevel;
    }

    public void setWordLevel(int wordLevel) {
        this.wordLevel = wordLevel;
    }
}
