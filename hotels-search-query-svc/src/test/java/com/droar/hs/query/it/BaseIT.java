package com.droar.hs.query.it;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
public class BaseIT {

    private static final Integer POSTGRES_PORT = 5432;
    private static final String POSTGRESQL_DB_IMAGE = "postgres:14.1-alpine";

    @Container
    static GenericContainer<?> postgresContainer = new GenericContainer(DockerImageName.parse(POSTGRESQL_DB_IMAGE))
            .withExposedPorts(POSTGRES_PORT)
            .withEnv("POSTGRES_USERNAME", "postgres")
            .withEnv("POSTGRES_PASSWORD", "postgres")
            .withEnv("POSTGRES_DB", "postgres")
            .withReuse(true);

    @DynamicPropertySource
    static void registerPostgresDbProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:postgresql://localhost:" + postgresContainer.getFirstMappedPort().toString() + "/postgres");
    }

    @Autowired
    protected MockMvc mockMvc;
}