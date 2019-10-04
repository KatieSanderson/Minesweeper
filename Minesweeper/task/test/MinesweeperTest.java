import minesweeper.Main;
import org.hyperskill.hstest.dev.dynamic.output.SystemOutHandler;
import org.hyperskill.hstest.dev.stage.BaseStageTest;
import org.hyperskill.hstest.dev.testcase.CheckResult;
import org.hyperskill.hstest.dev.testcase.TestCase;

import java.util.*;
import java.util.stream.Collectors;

public class MinesweeperTest extends BaseStageTest<Integer> {

    public MinesweeperTest() {
        super(Main.class);
    }

    @Override
    public List<TestCase<Integer>> generate() {
        List<TestCase<Integer>> tests = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            TestCase<Integer> test = new TestCase<Integer>()
                .addInput("" + i)
                .setAttach(i);
            tests.add(test);
            tests.add(test);
        }
        return tests;
    }

    @Override
    public CheckResult check(String reply, Integer attach) {

        String outputSinceLastInput = SystemOutHandler.getDynamicOutput().trim();

        List<String> lines =
            Arrays.stream(outputSinceLastInput.split("\n"))
                .map(String::trim)
                .collect(Collectors.toList());

        if (lines.isEmpty()) {
            return CheckResult.FALSE(
                "Looks like you didn't output a single line!"
            );
        }

        if (lines.size() != 9) {
            return CheckResult.FALSE(
                "You should output exactly 9 lines of the field. Found: " + lines.size() + "."
            );
        }

        int mines = 0;

        for (String line : lines) {
            if (line.length() != 9) {
                return CheckResult.FALSE(
                    "One of the lines of the field doesn't have 9 symbols, " +
                        "but has " + line.length() + ".\n" +
                        "This line is \"" + line + "\""
                );
            }

            for (char c : line.toCharArray()) {
                if (c != 'X' && c != '.' && !(c >= '0' && c <= '9')) {
                    return CheckResult.FALSE(
                        "One of the characters is not equal to 'X' or '.' or to a number.\n" +
                            "In this line: \"" + line + "\"."
                    );
                }
                if (c == 'X') {
                    mines++;
                }
            }
        }

        if (attach != mines) {
            return CheckResult.FALSE(
                "Expected to see " + attach + " mines, found " + mines
            );
        }

        int[] around = new int[] {-1, 0, 1};

        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);

                if (c == 'X') {
                    continue;
                }

                int minesAround = 0;

                for (int dx : around) {
                    for (int dy : around) {

                        int newX = x + dx;
                        int newY = y + dy;

                        if (0 <= newX && newX < 9 &&
                            0 <= newY && newY < 9) {

                            char newC = lines.get(newY).charAt(newX);

                            if (newC == 'X') {
                                minesAround++;
                            }
                        }
                    }
                }

                if (minesAround == 0 && c != '.') {
                    return CheckResult.FALSE(
                        "There are no mines around, but found number " + c + ".\n" +
                            "In line " + (y+1) + ", symbol " + (x+1) + "."
                    );
                }

                if (minesAround != 0 && c != '0' + minesAround) {
                    return CheckResult.FALSE(
                        "In this cell should be number " + minesAround + ", " +
                            "but found symbol \"" + c + "\".\n" +
                            "In line " + (y+1) + ", symbol " + (x+1) + "."
                    );
                }

            }
        }

        return CheckResult.TRUE;
    }
}