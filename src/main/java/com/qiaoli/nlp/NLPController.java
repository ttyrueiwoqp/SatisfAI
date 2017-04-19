package com.qiaoli.nlp;

import com.google.cloud.language.spi.v1.LanguageServiceClient;

import com.google.cloud.language.v1.AnalyzeSentimentResponse;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Document.Type;
import com.google.cloud.language.v1.Sentiment;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class NLPController {

    @RequestMapping("/")
    public String home() {
        return "Hello World!";
    }

    @GetMapping("/analyse/{text}")
    public String analyse(@PathVariable String text) throws IOException {

        // Instantiates a client
        LanguageServiceClient language = LanguageServiceClient.create();
        // The text to analyze
        Document doc = Document.newBuilder()
                .setContent(text).setType(Type.PLAIN_TEXT).build();

        // Detects the sentiment of the text

        Sentiment sentiment = language.analyzeSentiment(doc).getDocumentSentiment();

        System.out.println(text + text);

        String sb = "Text: " + text + "\n" +
                "Sentiment: " + sentiment.getScore() + sentiment.getMagnitude();

        return sb;
    }

    @GetMapping("/test/{text}")
    public @ResponseBody
    Foo test(@PathVariable String text) {
        return new Foo(1, text);
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