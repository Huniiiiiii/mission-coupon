package mission.View;

import java.util.Scanner;

public class View {
    private final Scanner scanner = new Scanner(System.in);

    public void printMenu() {
        System.out.println("쿠폰 적립 시스템입니다. 사용할 기능을 선택해주세요.");
        System.out.println("1) 회원 등록, 2) 쿠폰 검색, 3) 쿠폰 적립, 4) 쿠폰 사용 5) 프로그램 종료");
    }

    public String input(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}
