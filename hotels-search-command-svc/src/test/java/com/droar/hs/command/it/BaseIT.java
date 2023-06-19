package com.droar.hs.command.it;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
public class BaseIT {

    private static final Integer POSTGRES_PORT = 5432;
    private static final Integer KAFKA_PORT = 9093;
    private static final Integer ZOOKEPER_PORT = 2181;
    private static final String POSTGRESQL_DB_IMAGE = "postgres:14.1-alpine";
    private static final String KAFKA_IMAGE = "confluentinc/cp-kafka:6.2.1";
    private static final String ZOOKEEPER_TEST_IMAGE = "confluentinc/cp-zookeeper:4.0.0";

    private static final Network network = Network.newNetwork();

    @Container
    static GenericContainer<?> postgresContainer = new GenericContainer(DockerImageName.parse(POSTGRESQL_DB_IMAGE))
            .withExposedPorts(POSTGRES_PORT)
            .withNetwork(network)
            .withEnv("POSTGRES_USERNAME", "postgres")
            .withEnv("POSTGRES_PASSWORD", "postgres")
            .withEnv("POSTGRES_DB", "postgres")
            .withReuse(true);

    @Container
    static GenericContainer<?> zookeeper = new GenericContainer<>(DockerImageName.parse(ZOOKEEPER_TEST_IMAGE))
            .withNetwork(network)
            .withExposedPorts(ZOOKEPER_PORT)
            .withNetworkAliases("zookeeper")
            .withEnv("ZOOKEEPER_CLIENT_PORT", "2181")
            .withEnv("ZOOKEEPER_TICK_TIME", "2000");

    @Container
    static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse(KAFKA_IMAGE))
            .withNetwork(network)
            .dependsOn(zookeeper)
            .withExposedPorts(KAFKA_PORT)
            .withEnv("KAFKA_LISTENERS",  "PLAINTEXT://0.0.0.0:" + KAFKA_PORT + ",BROKER://0.0.0.0:9092")
            .withEnv("KAFKA_LISTENER_SECURITY_PROTOCOL_MAP", "BROKER:PLAINTEXT,PLAINTEXT:PLAINTEXT")
            .withEnv("KAFKA_INTER_BROKER_LISTENER_NAME", "BROKER")

            .withEnv("KAFKA_BROKER_ID", "1")
            .withEnv("KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR", "1")

            .withExternalZookeeper("zookeeper:2181");

    @DynamicPropertySource
    static void registerPostgresDbProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
        registry.add("spring.datasource.url", () -> "jdbc:postgresql://localhost:" + postgresContainer.getFirstMappedPort().toString() + "/postgres");
    }

    @Autowired
    protected MockMvc mockMvc;
}