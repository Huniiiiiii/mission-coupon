package mission.View;

import java.util.Scanner;

public class View {
    private final Scanner scanner = new Scanner(System.in);

    public void printMenu() {
        System.out.println("\n=== 쿠폰 적립 시스템 ===");
        System.out.println("1) 회원 등록");
        System.out.println("2) 쿠폰 조회");
        System.out.println("3) 쿠폰 적립");
        System.out.println("4) 쿠폰 사용");
        System.out.println("5) 프로그램 종료");
        System.out.println("=====================");
    }

    public String input(String message) {
        System.out.print(message + " ");
        return scanner.nextLine().trim();
    }

    public void showMessage(String message) {
        System.out.println("[안내] " + message);
    }
}
