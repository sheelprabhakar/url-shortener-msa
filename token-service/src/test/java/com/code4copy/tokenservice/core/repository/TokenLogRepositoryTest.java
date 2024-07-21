package com.code4copy.tokenservice.core.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.code4copy.tokenservice.core.entity.TokenLogEntity;
import java.util.Calendar;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DirtiesContext
@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TokenLogRepositoryTest {
  @Container
  private static final MySQLContainer database = new MySQLContainer("mysql");

  @Autowired
  private TokenLogRepository tokenLogRepository;


  @DynamicPropertySource
  static void databaseProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", database::getJdbcUrl);
    registry.add("spring.datasource.username", database::getUsername);
    registry.add("spring.datasource.password", database::getPassword);
  }

  @Test
  @Order(1)
  void test_repository_object_ok() {
    assertNotNull(this.tokenLogRepository);
  }

  @Test
  @Order(2)
  void test_save_valid_entity_ok() {
    TokenLogEntity log = new TokenLogEntity();
    log.setCreatedAt(Calendar.getInstance());
    log.setCreatedBy("sheel");
    log.setRange(1, 10);
    assertNotNull(this.tokenLogRepository.save(log));
  }

  @Test
  @Order(3)
  void test_save_duplicate_entity_not_ok() {
    TokenLogEntity log = new TokenLogEntity();
    log.setCreatedAt(Calendar.getInstance());
    log.setCreatedBy("sheel");
    log.setRange(11, 20);
    assertNotNull(this.tokenLogRepository.save(log));
  }

  @Test
  @Order(4)
  void test_save_valid_entity_nullable_ok() {
    TokenLogEntity log = new TokenLogEntity();
    log.setCreatedAt(Calendar.getInstance());

    log.setRange(21, 30);
    assertNotNull(this.tokenLogRepository.save(log));
  }

  @Test
  @Order(5)
  void test_update_valid_entity_not_ok() {
    TokenLogEntity log = new TokenLogEntity();
    log.setCreatedAt(Calendar.getInstance());
    log.setCreatedBy("sheel");
    log.setRange(31, 40);
    TokenLogEntity logDO = this.tokenLogRepository.save(log);

    logDO.setCreatedBy("sindhu");
    TokenLogEntity logDO1 = this.tokenLogRepository.save(logDO);
    assertEquals(logDO1.getCreatedBy(), logDO.getCreatedBy());

    TokenLogEntity byId = this.tokenLogRepository.findById(logDO1.getId()).get();
    assertEquals(byId.getCreatedAt(), logDO1.getCreatedAt());
  }

  @Test
  @Order(6)
  void test_repository_get_max_ok() {
    TokenLogEntity log = new TokenLogEntity();
    log.setCreatedAt(Calendar.getInstance());
    log.setCreatedBy("sheel");
    log.setRange(41, 50);
    TokenLogEntity logDO = this.tokenLogRepository.save(log);
    assertNotNull(logDO);
    assertEquals(50, this.tokenLogRepository.maxToNumber());
  }
}
