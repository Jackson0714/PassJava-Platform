package com.jackson0714.passjava.question.controller;

import com.jackson0714.common.utils.PageUtils;
import com.jackson0714.common.utils.R;
import com.jackson0714.passjava.question.entity.TypeEntity;
import com.jackson0714.passjava.question.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 题目-题目类型表
 *
 * @author jackson0714
 * @email jackson0585@163.com
 * @date 2020-04-25 22:34:04
 */
@RestController
@RequestMapping("question/v1/app/type")
public class TypeAppController {

    private Map<String, Object> cache = new HashMap<>();

    @Autowired
    private ITypeService ITypeService;

    /**
     * 查询
     */
    @RequestMapping("/list")
    //@RequiresPermissions("question:type:list")
    public R list(){
        List<TypeEntity> typeEntityListCache = (List<TypeEntity>) cache.get("typeEntityList");
        List<TypeEntity> typeEntityList = null;
        if (typeEntityListCache == null) {
            System.out.println("The cache is empty");
            typeEntityList = ITypeService.list();
            cache.put("typeEntityList", typeEntityList);
        }
        return R.ok().put("typeEntityList", typeEntityList);
    }
}
