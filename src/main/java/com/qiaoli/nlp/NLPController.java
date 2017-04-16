package com.qiaoli.nlp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NLPController {

    @RequestMapping("/")
    public String home() {
        return "Hello World!";
    }

    @GetMapping("/analyse/{text}")
    public String analyse(@PathVariable String text) {
        System.out.println(text + text);
        return text + text;
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