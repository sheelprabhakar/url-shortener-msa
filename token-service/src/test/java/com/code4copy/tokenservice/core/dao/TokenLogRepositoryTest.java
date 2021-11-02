package com.code4copy.tokenservice.core.dao;

import com.code4copy.tokenservice.core.domain.TokenLogDO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext
@DataJpaTest
public class TokenLogRepositoryTest {
    @Autowired
    private TokenLogRepository tokenLogRepository;

    @Test
    @Order(1)
    public void test_repository_object_ok(){
        assertNotNull(this.tokenLogRepository);
    }

    @Test
    @Order(2)
    public void test_save_valid_entity_ok(){
        TokenLogDO log = new TokenLogDO();
        log.setCreateAt(Calendar.getInstance());
        log.setCreatedBy("sheel");
        log.setRange(1, 10);
        assertNotNull(this.tokenLogRepository.save(log));
    }

    @Test
    @Order(3)
    public void test_save_duplicate_entity_not_ok(){
        TokenLogDO log = new TokenLogDO();
        log.setCreateAt(Calendar.getInstance());
        log.setCreatedBy("sheel");
        log.setRange(11, 20);
        assertNotNull(this.tokenLogRepository.save(log));

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            final TokenLogDO log1 = new TokenLogDO();
            log1.setCreateAt(Calendar.getInstance());
            log1.setCreatedBy("sheel");
            log1.setRange(11, 20);
            assertNotNull(this.tokenLogRepository.save(log1));
        });

    }

    @Test
    @Order(4)
    public void test_save_valid_entity_nullable_ok(){
        TokenLogDO log = new TokenLogDO();
        log.setCreateAt(Calendar.getInstance());

        log.setRange(21, 30);
        assertNotNull(this.tokenLogRepository.save(log));
    }

    @Test
    @Order(5)
    public void test_update_valid_entity_not_ok(){
        TokenLogDO log = new TokenLogDO();
        log.setCreateAt(Calendar.getInstance());
        log.setCreatedBy("sheel");
        log.setRange(31, 40);
        TokenLogDO logDO = this.tokenLogRepository.save(log);

        logDO.setCreatedBy("sindhu");
        TokenLogDO logDO1 = this.tokenLogRepository.save(logDO);
        assertEquals(logDO1.getCreatedBy(),logDO.getCreatedBy());

        TokenLogDO byId = this.tokenLogRepository.findById(logDO1.getId()).get();
        assertNotEquals(byId.getCreateAt(), logDO1.getCreateAt());
    }

    @Test
    @Order(6)
    public void test_repository_get_max_ok(){
        TokenLogDO log = new TokenLogDO();
        log.setCreateAt(Calendar.getInstance());
        log.setCreatedBy("sheel");
        log.setRange(41, 50);
        TokenLogDO logDO = this.tokenLogRepository.save(log);
        assertEquals( this.tokenLogRepository.maxToNumber(), 50);
    }
}
