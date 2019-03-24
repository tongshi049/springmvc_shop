package com.example.o2o;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * config spring and junit, start up junit loading springIOC container
 */

@RunWith(SpringJUnit4ClassRunner.class)
// tell junit where spring config file is
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml",
        "classpath:spring/spring-redis.xml"})
public class BaseTest {

}
