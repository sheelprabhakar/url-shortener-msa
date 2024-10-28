package com.code4copy.shortnerservice.config;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.SSLContext;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.http.ssl.TLS;
import org.apache.hc.core5.pool.PoolConcurrencyPolicy;
import org.apache.hc.core5.pool.PoolReusePolicy;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableRetry
@EnableCaching
@EnableCassandraRepositories
public class ApplicationConfig {
  @Value("${spring.profiles.active}")
  private String activeProfiles;

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder)
      throws Exception {
    if(!activeProfiles.equals("dev")){
      return builder.build();
    }
    // Else for MTLS test
    KeyStore trustStore = KeyStore.getInstance("PKCS12");
    try (InputStream trustStoreStream = new ClassPathResource("client_truststore.p12").getInputStream()) {
      trustStore.load(trustStoreStream, "changeit".toCharArray());
    } catch (CertificateException e) {
      throw new RuntimeException(e);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }

    KeyStore keyStore = KeyStore.getInstance("PKCS12");
    try (InputStream keyStoreStream = new ClassPathResource("client.p12").getInputStream()) {
      keyStore.load(keyStoreStream, "changeit".toCharArray());
    }

    SSLContext sslContext = SSLContextBuilder.create()
        .loadTrustMaterial(trustStore, null)
        .loadKeyMaterial(keyStore, "changeit".toCharArray())
        .build();

    PoolingHttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
        .setSSLSocketFactory(SSLConnectionSocketFactoryBuilder.create()
            .setSslContext(sslContext)
            .setTlsVersions(TLS.V_1_3)
            .build())
        .setDefaultSocketConfig(SocketConfig.custom()
            .setSoTimeout(Timeout.ofMinutes(1))
            .build())
        .setPoolConcurrencyPolicy(PoolConcurrencyPolicy.STRICT)
        .setConnPoolPolicy(PoolReusePolicy.LIFO)
        .setDefaultConnectionConfig(ConnectionConfig.custom()
            .setSocketTimeout(Timeout.ofMinutes(1))
            .setConnectTimeout(Timeout.ofMinutes(1))
            .setTimeToLive(TimeValue.ofMinutes(10))
            .build())
        .build();

    CloseableHttpClient httpClient = HttpClients.custom()
        .setConnectionManager(connectionManager)
        .build();

    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
    return new RestTemplate(factory);
  }


  @Bean
  public RetryTemplate retryTemplate() {
    RetryTemplate retryTemplate = new RetryTemplate();

    FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
    fixedBackOffPolicy.setBackOffPeriod(500L);
    retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

    SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
    retryPolicy.setMaxAttempts(3);
    retryTemplate.setRetryPolicy(retryPolicy);

    return retryTemplate;
  }

}
