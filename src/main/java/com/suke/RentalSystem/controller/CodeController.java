package com.suke.RentalSystem.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.suke.RentalSystem.core.Result;
import com.suke.RentalSystem.core.ResultGenerator;
import com.suke.RentalSystem.entity.Code;
import com.suke.RentalSystem.service.CodeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping({"/code"})
public class CodeController {
    @Resource
    private CodeService codeService;

    @PostMapping
    public Result add(@Validated @RequestBody Code code) {
        this.codeService.saveCode(code);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping({"/{id}"})
    public Result delete(@PathVariable Long id) {
        this.codeService.deleteByPK(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@Validated @RequestBody Code code) {
        this.codeService.updateCode(code);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping({"/{id}"})
    public Result detail(@PathVariable Long id) {
        Code code = (Code)this.codeService.findById(id);
        return ResultGenerator.genSuccessResult(code);
    }

    @GetMapping({"/type"})
    public Result listByGroup(@RequestParam String type) {
        List<Code> codeList = this.codeService.listCodeByCond((String)null, type);
        return ResultGenerator.genSuccessResult(codeList);
    }

    @GetMapping({"/list"})
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size, @RequestParam(required = false) String keyword, @RequestParam(required = false) String codeGroupCode) {
        PageHelper.startPage(page, size);
        List<Code> list = this.codeService.listCodeByCond(keyword, codeGroupCode);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page.intValue(), size.intValue());
        List<Code> list = this.codeService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @GetMapping("/name")
    public Result name(@RequestParam String type, @RequestParam String code){
        String name = this.codeService.getCodeDesc(type, code);
        return ResultGenerator.genSuccessResult(name);
    }
}
