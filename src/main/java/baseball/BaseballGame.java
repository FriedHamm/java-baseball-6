package baseball;

import camp.nextstep.edu.missionutils.Console;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class BaseballGame {
    private static Boolean isGameOn = true;
    private static Boolean newGame = false;
    private static LinkedHashSet<Integer> userNumber = null;

    private BaseballGame() {
    }

    public final static void turnOn() {
        BaseballOutput.gameStart();
        BaseModel.newbaseballNumber();
        mainGame();
    }

    private static void mainGame() {
        while (isGameOn) {
            setting();
            exceptionCheck();
            if (!isGameOn) {
                isGameOn = true;
                break;
            }
            printBns(BaseModel.judgeNumber(BaseModel.generateUNumber(userNumber)));
            newOrEnd();
            if (!isGameOn && !newGame) {
                isGameOn = true;
                break;
            }
            newGame = false;
        }
    }

    private static void exceptionCheck() throws IllegalArgumentException {
        try {
            BaseModel.exceptionCheck(userNumber);
        } catch (IllegalArgumentException e) {
            isGameOn = false;
            throw e;
        }
    }

    private static void newOrEnd() {
        if (!isGameOn) {
            BaseballOutput.printNewOrEnd();
            newGame = BaseModel.newOrEnd(Console.readLine());
            if (newGame) {
                BaseModel.newbaseballNumber();
                isGameOn = true;
            }
        }
    }

    private static void setting() {
        BaseModel.generateBNumber();
        BaseballOutput.printInputUserNum();
        userNumber = BaseModel.forUNumberChecking(Console.readLine());
    }

    private static void printBns(int[] bns) {
        if (bns[0] == 0 && bns[1] == 0) {
            BaseballOutput.printCall();
            return;
        } else if (bns[0] == 0) {
            BaseballOutput.printCall(new StrikeCall(bns[1]));
            if (bns[1] == 3) {
                isGameOn = false;
            }
            return;
        } else if (bns[1] == 0) {
            BaseballOutput.printCall(new BallCall(bns[0]));
            return;
        }
        BaseballOutput.printCall(new BallCall(bns[0]), new StrikeCall(bns[1]));
    }
}

class BaseballOutput {
    private BaseballOutput() {
    }

    static void gameStart() {
        System.out.println("숫자 야구 게임을 시작합니다.");
    }

    static void printInputUserNum() {
        System.out.println("숫자를 입력해주세요 :");
    }

    static void printCall(BallCount... ballCounts) {
        for (BallCount i : ballCounts) {
            i.print();
        }
    }

    static void printCall() {
        System.out.println("낫싱");
    }

    static void printNewOrEnd() {
        System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료");
        System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
    }


}

class StrikeCall implements BallCount {
    int count = 0;

    public StrikeCall(int count) {
        this.count = count;
    }

    @Override
    public void print() {
        System.out.println(count + "스트라이크");
    }
}

class BallCall implements BallCount {
    int count = 0;

    public BallCall(int count) {
        this.count = count;
    }

    @Override
    public void print() {
        System.out.printf("%d볼 ", count);
    }
}

class BaseModel {

    private BaseModel() {
    }

    static LinkedHashSet<Integer> baseballNumber = null;
    private static Iterator<Integer> bNumber = null;

    static void newbaseballNumber() {
        baseballNumber = NumberGenerator.numberGenerating(3);
    }

    static void generateBNumber() {
        bNumber = baseballNumber.iterator();
    }

    static Iterator<Integer> generateUNumber(LinkedHashSet<Integer> userNumber) {
        return userNumber.iterator();
    }

    static LinkedHashSet<Integer> forUNumberChecking(String userNumber) {
        LinkedHashSet<Integer> userNumberRet = new LinkedHashSet<>();
        for (int i = 0; i < userNumber.length(); i++) {
            userNumberRet.add(userNumber.charAt(i) - '0');
        }
        return userNumberRet;
    }

    static boolean newOrEnd(String userChoice) {

        if (userChoice.equals("1")) {
            return true;
        }
        return false;
    }

    static void exceptionCheck(LinkedHashSet<Integer> userNumber) throws IllegalArgumentException {
        if (userNumber.size() != 3) {
            throw new IllegalArgumentException("please input 3 digit");
        }
        for (int i : userNumber) {
            if (i < 1 || i > 9) {
                throw new IllegalArgumentException("please input the exact data");
            }
        }
    }

    static int[] judgeNumber(Iterator<Integer> uNumber) {
        LinkedHashSet<Integer> temp = new LinkedHashSet<>();
        int first = 0;
        int second = 0;
        int strike = 0;

        while (bNumber.hasNext() && uNumber.hasNext()) {
            first = bNumber.next();
            second = uNumber.next();
            System.out.println(first);
            temp.add(first);
            temp.add(second);

            if (first == second) {
                strike++;
            }

        }
        return new int[]{(6 - temp.size() - strike), strike};
    }
}


