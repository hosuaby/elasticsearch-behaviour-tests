package io.hosuaby.elastic.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest.AliasActions;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RequestOptions;
import org.junit.jupiter.api.Test;

public class AliasTests extends AbstractElasticsearchTest {

    @Test
    public void testTryRetrieveAliasImmediatelyAfterCreation() throws IOException {

        /* Given */
        String indexName = randomIndexName();
        String alias = aliasForIndex(indexName);

        client.indices().create(new CreateIndexRequest(indexName), RequestOptions.DEFAULT);

        AliasActions aliasActionCreate = new AliasActions(AliasActions.Type.ADD)
                .index(indexName)
                .alias(alias);
        IndicesAliasesRequest request = new IndicesAliasesRequest().addAliasAction(aliasActionCreate);
        client.indices().updateAliases(request, RequestOptions.DEFAULT);

        /* When */
        GetAliasesResponse getAliasesResponse = client
                .indices()
                .getAlias(new GetAliasesRequest(alias), RequestOptions.DEFAULT);

        /* Then */
        assertThat(getAliasesResponse.getAliases())
                .isNotEmpty()
                .containsKey(indexName);

        client.indices().delete(new DeleteIndexRequest(indexName), RequestOptions.DEFAULT);
    }

    static String randomIndexName() {
        return RandomStringUtils.randomAlphabetic(4).toLowerCase();
    }

    static String aliasForIndex(String indexName) {
        return indexName + "_alias";
    }
}
