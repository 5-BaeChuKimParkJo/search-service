package org.example.searchservice.adapter.out.elasticsearch.common;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NonNull;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.elasticsearch.client.RestClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Configuration
public class ElasticSearchConfig extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.uris}")
    String url;

    @Value("${spring.elasticsearch.username}")
    String username;

    @Value("${spring.elasticsearch.password}")
    String password;

    @Bean
    @Primary
    public ElasticsearchClient elasticsearchClient() {

        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(MapperFeature.REQUIRE_HANDLERS_FOR_JAVA8_TIMES);

        JacksonJsonpMapper jsonpMapper = new JacksonJsonpMapper(objectMapper);

        RestClient restClient = RestClient.builder(HttpHost.create(url))
                .setHttpClientConfigCallback(httpClientBuilder -> {
                    BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                    credentialsProvider.setCredentials(
                            AuthScope.ANY,
                            new UsernamePasswordCredentials(username, password)
                    );
                    return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
                            .setKeepAliveStrategy(
                                    (response, context) -> 60000L // Set keep-alive to 60 seconds
                            );
                })
                .build();

        RestClientTransport transport = new RestClientTransport(restClient, jsonpMapper);


        return new ElasticsearchClient(transport);
    }

    @Override
    @NonNull
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(url)
                .withBasicAuth(username, password)
                .build();
    }
}
