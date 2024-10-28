package com.code4copy.shortnerservice.rest.controller;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import com.code4copy.shortnerservice.ShortenerServiceApplication;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.redis.testcontainers.RedisStackContainer;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.containers.wait.CassandraQueryWaitStrategy;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(classes = ShortenerServiceApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public abstract class AbstractIntegrationTest {
  @Autowired
  MockMvc mockMvc;

  @Container
  public static final CassandraContainer cassandra
      = (CassandraContainer) new CassandraContainer("cassandra:latest").withExposedPorts(9042)
      .waitingFor(new CassandraQueryWaitStrategy());

  @Container
  public static final RedisStackContainer redis
      = new RedisStackContainer("redis:latest").withExposedPorts(6379);

  static ClientAndServer mockServer;

  @DynamicPropertySource
  static void setupCassandraConnectionProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.cassandra.keyspace-name", "shorturldb"::toString);
    registry.add("spring.cassandra.contact-points", cassandra::getHost);
    registry.add("spring.cassandra.port", cassandra::getFirstMappedPort);
    registry.add("spring.cassandra.local-datacenter", cassandra::getLocalDatacenter);
    registry.add("spring.cassandra.schema-action", "create_if_not_exists"::toString);

    createKeyspace(cassandra.getContactPoint(), cassandra.getFirstMappedPort());

    mockServer = startClientAndServer(8089);
    createExpectationForGetToken();

    registry.add("spring.redis.database", "0"::toString);
    registry.add("spring.redis.host", redis::getHost);
    registry.add("spring.redis.port", redis::getFirstMappedPort);
    registry.add("spring.redis.timeout", "60000"::toString);

    redis.start();
    cassandra.start();
  }


  private static void createExpectationForGetToken() {
    new MockServerClient("localhost", 8089)
        .when(
            request()
                .withMethod("GET")
                .withPath("/tokenservice/api/v1/next/"))
        .respond(
            response()
                .withStatusCode(200)
                .withHeaders(
                    new Header("Content-Type", "application/json; charset=utf-8"))
                .withBody("{ \"fromNumber\":\"1\", \"toNumber\":\"1000\" }")
                .withDelay(TimeUnit.MILLISECONDS, 200)
        );
  }

  static void createKeyspace(InetSocketAddress inetSocketAddress, int mappedPort) {
    Cluster cluster = getCluster(inetSocketAddress, mappedPort, false);
    try (Session session = cluster.connect()) {
      session.execute("CREATE KEYSPACE IF NOT EXISTS shorturldb WITH replication = \n" +
          "{'class':'SimpleStrategy','replication_factor':'1'};");
    }
  }

  static Cluster getCluster(InetSocketAddress inetSocketAddress, int mappedPort,
                            boolean enableJmxReporting) {
    Cluster.Builder builder = Cluster.builder().addContactPoint(inetSocketAddress.getHostName())
        .withPort(mappedPort);
    if (!enableJmxReporting) {
      builder.withoutJMXReporting();
    }

    return builder.build();
  }

}
