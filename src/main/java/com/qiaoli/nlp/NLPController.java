package com.qiaoli.nlp;

import com.google.cloud.language.spi.v1.LanguageServiceClient;

import com.google.cloud.language.v1.*;
import com.google.cloud.language.v1.Document.Type;

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

    @GetMapping("/analyse/{text}")
    public String analyse(@PathVariable String text) throws IOException {

        LanguageServiceClient language = LanguageServiceClient.create();
        Document doc = Document.newBuilder()
                .setContent(text).setType(Type.PLAIN_TEXT).build();

        Sentiment sentiment = language.analyzeSentiment(doc).getDocumentSentiment();

        System.out.println(text + text);

        String sb = "Text: " + text + "\n" +
                "Sentiment: " + sentiment.getScore() + sentiment.getMagnitude();

        return sb;
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