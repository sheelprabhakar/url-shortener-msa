package com.code4copy.redirectservice.rest;

import com.code4copy.redirectservice.RedirectServiceApplication;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.concurrent.TimeUnit;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@SpringBootTest(classes = RedirectServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public abstract class AbstractIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Container
    public static final CassandraContainer cassandra
            = (CassandraContainer) new CassandraContainer("cassandra:3.11.2").withExposedPorts(9042);

    @Container
    public static final GenericContainer redis
            = (GenericContainer) new GenericContainer("redis:3.0.6").withExposedPorts(6379);

    static ClientAndServer mockServer;
    @BeforeAll
    static void setupCassandraConnectionProperties() {
        System.setProperty("spring.data.cassandra.keyspace-name", "shorturldb");
        System.setProperty("spring.data.cassandra.contact-points", cassandra.getContainerIpAddress());
        System.setProperty("spring.data.cassandra.port", String.valueOf(cassandra.getMappedPort(9042)));
        System.setProperty("spring.data.cassandra.local-datacenter", "datacenter1");
        System.setProperty("spring.data.cassandra.schema-action", "create_if_not_exists");

        createKeyspace(cassandra.getCluster());

        mockServer = startClientAndServer(8089);
        createExpectationForGetToken();

        System.setProperty("spring.redis.database", "0");
        System.setProperty("spring.redis.host", redis.getContainerIpAddress());
        System.setProperty("spring.redis.port", String.valueOf(redis.getMappedPort(6379)));
        System.setProperty("spring.redis.timeout", "60000");

        redis.start();
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
                                .withDelay(TimeUnit.MILLISECONDS,200)
                );
    }

    @AfterAll
    public static void stopServer() {
        mockServer.stop();
        redis.start();
    }
    static void createKeyspace(Cluster cluster) {
        try(Session session = cluster.connect()) {
            session.execute("CREATE KEYSPACE IF NOT EXISTS shorturldb WITH replication = \n" +
                    "{'class':'SimpleStrategy','replication_factor':'1'};");
        }
    }

}
