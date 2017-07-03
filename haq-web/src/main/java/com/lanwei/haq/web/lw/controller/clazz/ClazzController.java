package com.lanwei.haq.web.lw.controller.clazz;

import com.lanwei.haq.web.lw.service.clazz.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @作者：刘新运
 * @日期：2017/6/25 16:11
 * @描述：类
 */
@Controller
@RequestMapping("/clazz")
public class ClazzController {

    private final ClazzService clazzService;

    @Autowired
    public ClazzController(ClazzService clazzService) {
        this.clazzService = clazzService;
    }

    @RequestMapping("/query")
    public String query(Model model){
        model.addAttribute("list", clazzService.query());
        return "clazz/list";
    }

}
