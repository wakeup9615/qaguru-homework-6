package simpleTests;

import org.junit.jupiter.api.*;

public class SimpleTest {

    @Test
    void simpleTest() {
        Assertions.assertTrue(3 > 2);
    }

    @Test
    void simpleTest1() {
        Assertions.assertTrue(3 < 2);
    }

    @Test
    void simpleTest2() {
        throw new RuntimeException("Просто другой эксепшн");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("===== Starting before All tests");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("======== Starting 1 before Every test");
    }

    @AfterEach
    void afterEach() {
        System.out.println("======== Starting 1 after Every test");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("===== Starting after All tests");
    }
}
