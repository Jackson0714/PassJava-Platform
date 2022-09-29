package com.jackson0714.passjava.question.controller;

import java.util.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jackson0714.passjava.question.entity.QuestionEntity;
import com.jackson0714.passjava.question.service.IQuestionService;
import com.jackson0714.passjava.common.utils.PageUtils;
import com.jackson0714.passjava.common.utils.R;

import javax.validation.Valid;


/**
 * 
 *
 * @author 公众号：悟空聊架构
 * @公众号：悟空聊架构
 * @date 2020-04-25 22:34:04
 */
@RestController
@RequestMapping("question/v1/admin/question")
@Slf4j
public class QuestionController {
    @Autowired
    private IQuestionService IQuestionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("question:question:list")
    public R list(Page<QuestionEntity> page, @RequestParam Map<String, Object> params){
        long time = System.currentTimeMillis();
        Page<QuestionEntity> page1 = (Page<QuestionEntity>) IQuestionService.queryPage1(page, params);

        System.out.println("耗时："+ (System.currentTimeMillis() - time));
        return R.ok().put("page", page1);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
//    @Cacheable({"hot"})
    public R info(@PathVariable("id") Long id) {
		QuestionEntity question = IQuestionService.info(id);
        return R.ok().put("question", question);
    }

    /**
     * 测试方法的返回结果是否被缓存了
     */
    @RequestMapping("/test")
    @Cacheable(value = "hot", key = "#root.method.name")
    public int test() {
        return 222;
    }

    @RequestMapping("/test2")
    @Cacheable(value = "hot", key = "#root.method.name")
    public int test2() {
        return 456;
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@Valid @RequestBody QuestionEntity question){
		IQuestionService.saveQuestion(question);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("question:question:update")
    public R update(@RequestBody QuestionEntity question){
		IQuestionService.updateQuestion(question);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @CacheEvict(value = "hot")
    public R delete(@RequestBody Long[] ids){
		IQuestionService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    @RequestMapping("/create")
    // mock create
    public R create(@Valid @RequestBody QuestionEntity question){
        IQuestionService.createQuestion(question);
        return R.ok();
    }

    @RequestMapping("/remove/{id}")
    @CacheEvict(value = "hot")
    public R remove(@PathVariable("id") Long id){
        IQuestionService.removeById(id);
        return R.ok();
    }

    @RequestMapping("/condition/{id}")
    @Cacheable(value = "hot", unless = "#result.message.containss('NoCache')")
    public R condition(@PathVariable("id") Long id) {
        QuestionEntity question = IQuestionService.info(id);
        HashMap<String, Object> map = new HashMap<String, Object>();

        return R.ok().put("question", question).put("message", "NoCache");
    }
}
