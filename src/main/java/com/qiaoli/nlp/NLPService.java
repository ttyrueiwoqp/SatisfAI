package com.qiaoli.nlp;

import com.google.cloud.language.spi.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Sentiment;
import com.qiaoli.nlp.dto.QnAnsDto;
import com.qiaoli.nlp.dto.QnSimilarDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

/**
 * @author Lu Fangjian
 */
@Service
public class NLPService {

    @Autowired
    private NLPDao nlpDao;

    public void initDB() {
        nlpDao.initDB();
    }

    public boolean createQnAns(QnAnsDto qnAnsDto) {
        qnAnsDto.setId(UUID.randomUUID().toString());
        return nlpDao.insertQnAnsDto(qnAnsDto);
    }

    public boolean createQnSimilar(QnSimilarDto qnSimilarDto) {
        qnSimilarDto.setId(UUID.randomUUID().toString());
        return nlpDao.insertQnSimilar(qnSimilarDto);
    }


    public String analyse(String text) throws IOException {
        LanguageServiceClient language = LanguageServiceClient.create();
        Document doc = Document.newBuilder()
                .setContent(text).setType(Document.Type.PLAIN_TEXT).build();

        Sentiment sentiment = language.analyzeSentiment(doc).getDocumentSentiment();

        System.out.println(text + text);

        return "Text: " + text + "\n" +
                "Sentiment: " + sentiment.getScore() + sentiment.getMagnitude();
    }
}
