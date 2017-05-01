package com.satisfai.nlp;

import com.google.cloud.language.spi.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Sentiment;
import com.satisfai.nlp.dto.FaqEntity;
import com.satisfai.nlp.dto.QnAnsDto;
import com.satisfai.nlp.dto.QnGroupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
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

    public void initData() {
        nlpDao.initData();
    }

    public boolean createQnAns(QnAnsDto qnAnsDto) {
        qnAnsDto.setQnGroupId(UUID.randomUUID().toString());
        return nlpDao.insertQnAnsDto(qnAnsDto);
    }

    public boolean createQnGroup(QnGroupDto qnGroupDto) {
        qnGroupDto.setQnGroupId(UUID.randomUUID().toString());
        return nlpDao.insertQnGroupDto(qnGroupDto);
    }

    public List<FaqEntity> findByQnText(String qnText) {
        return nlpDao.findByQnText(qnText);
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
