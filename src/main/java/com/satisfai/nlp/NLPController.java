package com.satisfai.nlp;

import com.satisfai.nlp.dto.FaqEntity;
import com.satisfai.nlp.dto.QnAnsDto;
import com.satisfai.nlp.dto.QnGroupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author Lu Fangjian
 */
@Controller
public class NLPController {

    @Autowired
    private NLPService nlpService;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "nlp";
    }

    @GetMapping("/initDB")
    public @ResponseBody
    String initDB() {
        nlpService.initDB();
        return "DB init";
    }

    @GetMapping("/initData")
    public @ResponseBody
    String initData() {
        nlpService.initData();
        return "Data init";
    }

    @GetMapping("/faq/{qnText}")
    public @ResponseBody
    List<FaqEntity> findByQnText(@PathVariable String qnText) {
        return nlpService.findByQnText(qnText);
    }

//    @PostMapping("/faq")
//    public @ResponseBody
//    FaqEntity findByQnText(@RequestBody String qnText) {
//        return nlpService.findByQnText(qnText);
//    }

    @PostMapping("/qnAns")
    public @ResponseBody
    boolean createQnAns(@RequestBody QnAnsDto qnAnsDto) {
        return nlpService.createQnAns(qnAnsDto);
    }

    @PostMapping("/qnGroup")
    public @ResponseBody
    boolean createQnGroup(@RequestBody QnGroupDto qnGroupDto) {
        return nlpService.createQnGroup(qnGroupDto);
    }

    @GetMapping("/analyse/{text}")
    public @ResponseBody
    String analyse(@PathVariable String text) throws IOException {
        return nlpService.analyse(text);
    }

    /**
     * <a href="https://cloud.google.com/appengine/docs/flexible/java/how-instances-are-managed#health_checking">
     * App Engine health checking</a> requires responding with 200 to {@code /_ah/health}.
     */
    @RequestMapping("/_ah/health")
    public String healthy() {
        // Message body required though ignored
        return "Still surviving.";
    }

}