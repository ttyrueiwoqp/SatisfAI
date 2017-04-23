package com.qiaoli.nlp;

import com.qiaoli.nlp.dto.QnAnsDto;
import com.qiaoli.nlp.dto.QnSimilarDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author Lu Fangjian
 */
@RestController
public class NLPController {

    @Autowired
    private NLPService nlpService;

    @RequestMapping("/")
    public String home() {
        return "Hello World!";
    }

    @GetMapping("/init")
    public void init() {
        nlpService.initDB();
    }

    @PostMapping("/QnAns")
    public boolean createQnAns(@RequestBody QnAnsDto qnAnsDto) {
        return nlpService.createQnAns(qnAnsDto);
    }

    @PostMapping("/QnSimilar")
    public boolean createQnAns(@RequestBody QnSimilarDto qnSimilarDto) {
        return nlpService.createQnSimilar(qnSimilarDto);
    }

    @GetMapping("/analyse/{text}")
    public String analyse(@PathVariable String text) throws IOException {
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