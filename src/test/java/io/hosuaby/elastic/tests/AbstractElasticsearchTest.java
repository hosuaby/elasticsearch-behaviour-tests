package io.hosuaby.elastic.tests;

import org.apache.http.HttpHost;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

abstract class AbstractElasticsearchTest {
    private static final HttpHost[] ES_HOSTS = {
            new HttpHost("localhost", 9201),
            new HttpHost("localhost", 9202),
            new HttpHost("localhost", 9203)
    };

    private static final int NB_THREADS = 4;

    protected RestHighLevelClient client = createClient();

    static RestHighLevelClient createClient() {
        RestClientBuilder clientBuilder = RestClient
                .builder(ES_HOSTS)
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                        .setDefaultIOReactorConfig(IOReactorConfig
                                .custom()
                                .setIoThreadCount(NB_THREADS)
                                .build()));

        return new RestHighLevelClient(clientBuilder);
    }
}
