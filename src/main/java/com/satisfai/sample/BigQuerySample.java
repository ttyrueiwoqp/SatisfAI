package com.satisfai.sample;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.Dataset;
import com.google.cloud.bigquery.DatasetInfo;

// Imports the Google Cloud client library
public class BigQuerySample {
    public static void main(String... args) throws Exception {
        // Instantiates a client
        BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();

        // The name for the new dataset
        String datasetName = "satisfai_nlp";

        // Prepares a new dataset
        Dataset dataset = null;
        DatasetInfo datasetInfo = DatasetInfo.newBuilder(datasetName).build();

        // Creates the dataset
        dataset = bigquery.create(datasetInfo);

        System.out.printf("Dataset %s created.%n", dataset.getDatasetId().getDataset());
    }
}