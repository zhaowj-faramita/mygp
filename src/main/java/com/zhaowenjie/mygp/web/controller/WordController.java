package com.zhaowenjie.mygp.web.controller;

import com.zhaowenjie.mygp.bean.Word;
import com.zhaowenjie.mygp.config.Message;
import com.zhaowenjie.mygp.config.MessageUtil;
import com.zhaowenjie.mygp.service.IWordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/word")
@CrossOrigin
@Api(description = "文档处理器")
public class WordController {

    @Autowired
    IWordService iWordService;

    @ApiOperation(value = "添加一个文档或者修改一个已有的文档")
    @PostMapping("/addOrUpdate")
    public Message addWord(Word word) {
        iWordService.addWord(word);
        System.out.println(word);
        return MessageUtil.success();
    }

    @ApiOperation("通过id删除题目")
    @GetMapping("/delete/byId")
    @ApiImplicitParam(name = "id",value = "要删除文档的主键",paramType = "query",required = true, dataType="Integer")
    public Message removeWord(int id) {
        iWordService.deleteWord(id);
        return MessageUtil.success();
    }

    @ApiOperation("通过题目删除题目")
    @GetMapping("/delete/byTitle")
    @ApiImplicitParam(name = "title",value = "要删除文档的题目",paramType = "query",required = true)
    public Message removeWordByTitle(String title) {
        iWordService.deleteWordByTitle(title);
        return MessageUtil.success();
    }

    @ApiOperation("根据主键查询文档")
    @GetMapping("/query/byId")
    @ApiImplicitParam(name = "id", value = "要查询文档的主键", paramType = "query", required = true, dataType="Integer")
    public Message<Word> queryWordById(int id) {
        return MessageUtil.success(iWordService.queryWordById(id));
    }

    @ApiOperation("查询所有文档")
    @GetMapping("/find/all")
    public Message<List<Word>> findAllWord() {
        return MessageUtil.success(iWordService.findAll());
    }

    @ApiOperation("查询所有文档，并通过上传时间排序")
    @GetMapping("/find/allOrderByPublishDate")
    public Message<List<Word>> findAllOrderByPublishDate() {
        return MessageUtil.success(iWordService.findAllOrderByPublishDate());
    }

    @ApiOperation("根据某文档id获取其从属文档")
    @GetMapping("/query/byParentId")
    @ApiImplicitParam(name = "ParentId", value = "要查找文档的主键", paramType = "query", required = true, dataType="Integer")
    public Message<List<Word>> findWordByParentId(int ParentId) {
        return MessageUtil.success(iWordService.queryWordByParent_id(ParentId));

    }

    @ApiOperation("根据文档级别获取文档")
    @GetMapping("/query/byWordLevel")
    @ApiImplicitParam(name = "WordLevel", value = "要查找文档的级别", paramType = "query", required = true, dataType="Integer")
    public Message<List<Word>> queryWordByWordLevel(int WordLevel) {
        return MessageUtil.success(iWordService.queryWordByWordLevel(WordLevel));
    }

    @ApiOperation("根据作者获取文档")
    @GetMapping("/query/byAuthor")
    @ApiImplicitParam(name = "author", value = "要查找文档的作者", paramType = "query", required = true)
    public Message<List<Word>> queryWordByAuthor(String author) {
        return MessageUtil.success(iWordService.queryWordByAuthor(author));
    }

    @ApiOperation("随机获取文档")
    @GetMapping("/query/byRandom")
    @ApiImplicitParam(name = "number", value = "要随机获取的个数", paramType = "query", required = true, dataType="Integer")
    public Message<List<Word>> queryWordByRandom(int number) {
        return MessageUtil.success(iWordService.randomQuery(number));
    }
}
