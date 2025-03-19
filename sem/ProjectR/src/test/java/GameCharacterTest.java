import cvut.cz.characters.GameCharacter;
import cvut.cz.items.Item;
import javafx.application.Preloader;
import javafx.stage.Screen;
import org.junit.jupiter.api.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class GameCharacterTest {
    private class GameCharacterTemp extends GameCharacter {}

    private final GameCharacterTemp testCharacter;
    private final static String TEST_JSON_DIRECTORY = "json_test_files";

    private static int failures = 0;
    private static int successes = 0;

    public GameCharacterTest() {
        testCharacter = new GameCharacterTemp();
    }
    @BeforeAll
    public static void setUp() {
        System.out.println("Starting testing of GameCharacter abstract class");
        System.out.println("=====================================================");
    }

    @AfterEach
    public void endDecor() {
        System.out.println("=====================================================");
    }

    @AfterAll
    public static void finish() {
        System.out.println("Finished testing of GameCharacter abstract class");
        System.out.println("Results:");
        System.out.println("\t*Successful: " + successes);
        System.out.println("\t*Failed: " + failures);
        System.out.println("Tests overall: " + (successes + failures));
    }

    @Test
    public void readAvailableItemsTest1() {
        testCharacter.readAvailableItems("src/test/resources/json_test_files/items_json_test.json");
        Item[] items = testCharacter.getItems();
        if (items == null) {
            failures++;
            System.out.println("[FAIL]Items were not read");
            return;
        }
        for (int i = 0 ; i < items.length ; i++) {
            System.out.println("Item" + (i + 1) + " attributes:");
            System.out.println(items[i].toString() + "\n");
        }
        successes++;
    }

    @Test
    public void writeAvailableItemsTest1() {
        testCharacter.readAvailableItems("src/test/resources/json_test_files/items_json_test.json");
        testCharacter.writeAvailableItems("src/test/resources/json_test_files/items_json_test_out.json");

        try(Scanner scanner = new Scanner(new FileInputStream("src/test/resources/json_test_files/items_json_test_out.json"))){
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        }catch(IOException e) {
            System.err.println("[FAIL]Items were not written");
            failures++;
            return;
        }
        successes++;
    }
}
