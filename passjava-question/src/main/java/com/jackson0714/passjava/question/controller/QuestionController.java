package com.jackson0714.passjava.question.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jackson0714.passjava.question.entity.QuestionEntity;
import com.jackson0714.passjava.question.service.IQuestionService;
import com.jackson0714.common.utils.PageUtils;
import com.jackson0714.common.utils.R;

import javax.validation.Valid;


/**
 * 
 *
 * @author jackson0714
 * @email jackson0585@163.com
 * @date 2020-04-25 22:34:04
 */
@RestController
@RequestMapping("question/v1/admin/question")
public class QuestionController {
    @Autowired
    private IQuestionService IQuestionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("question:question:list")
    public R list(@RequestParam Map<String, Object> params){
        long time = System.currentTimeMillis();
        PageUtils page = IQuestionService.queryPage(params);
        System.out.println("耗时："+ (System.currentTimeMillis() - time));
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("question:question:info")
    public R info(@PathVariable("id") Long id) {
		QuestionEntity question = IQuestionService.getById(id);
        return R.ok().put("question", question);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("question:question:save")
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
    //@RequiresPermissions("question:question:delete")
    public R delete(@RequestBody Long[] ids){
		IQuestionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 测试
     * @return
     */
    @RequestMapping("/test")
    //@RequiresPermissions("question:question:delete")
    public String test(){

        return "test";
    }

}
