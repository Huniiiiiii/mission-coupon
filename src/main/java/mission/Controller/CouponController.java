package mission.Controller;

import mission.Model.Member;
import mission.Model.MemberRepository;
import mission.View.View;

import java.util.List;

public class CouponController {
    private static final String PHONE_PATTERN = "^010-\\d{4}-\\d{4}$";
    private final MemberRepository repository = new MemberRepository();
    private final View view = new View();

    public void run() {
        while (true) {
            view.printMenu();
            String choice = view.input("");
            if (!handleMenu(choice)) break;
        }
    }

    private boolean handleMenu(String choice) {
        return switch (choice) {
            case "1" -> { registerMember(); yield true; }
            case "2" -> { showCouponStatus(); yield true; }
            case "3" -> { addCoupon(); yield true; }
            case "4" -> { useCoupon(); yield true; }
            case "5" -> {
                view.showMessage("프로그램을 종료합니다.");
                yield false;
            }
            default -> {
                view.showMessage("올바르지 않은 선택입니다.");
                yield true;
            }
        };
    }

    private void registerMember() {
        String name = view.input("이름을 입력해주세요.");
        String phone = view.input("전화번호를 입력해주세요 (ex. 010-1234-1234)");

        if (!isValidPhone(phone)) {
            view.showMessage("🚫 잘못된 전화번호 형식입니다. '010-1234-5678' 형식으로 입력해주세요.");
            return;
        }
        if (repository.exists(phone)) {
            view.showMessage("이미 등록된 전화번호입니다.");
            return;
        }

        repository.save(new Member(name, phone));
        view.showMessage("회원정보가 등록되었습니다.");
    }

    private void showCouponStatus() {
        String suffix = view.input("전화번호 뒤 네자리를 입력해주세요");
        List<Member> found = repository.findAllByPhoneSuffix(suffix);
        if (found.isEmpty()) {
            view.showMessage("해당 회원을 찾을 수 없습니다.");
            return;
        }
        if (found.size() == 1) {
            showCoupon(found.get(0));
            return;
        }
        identifyAndShow(found);
    }

    private void showCoupon(Member member) {
        view.showMessage(member.getName() + " 회원님의 현재 쿠폰은 " + member.getCoupons() + "개입니다.");
    }

    private void identifyAndShow(List<Member> members) {
        StringBuilder names = new StringBuilder();
        for (Member m : members) names.append(m.getName()).append(", ");
        String name = view.input("이름을 입력해 주세요? (" + names.substring(0, names.length() - 2) + ")");
        for (Member m : members)
            if (m.getName().equals(name)) {
                showCoupon(m);
                return;
            }
        view.showMessage("일치하는 이름이 없습니다.");
    }

    private void addCoupon() {
        Member member = identifyMember();
        if (member == null) return;

        view.showMessage("현재 적립된 쿠폰은 " + member.getCoupons() + "개 입니다.");
        try {
            int add = Integer.parseInt(view.input("적립할 쿠폰 갯수를 입력해주세요."));
            member.addCoupons(add);
            view.showMessage("쿠폰이 적립되었습니다.");
        } catch (NumberFormatException e) {
            view.showMessage("숫자를 입력해주세요.");
        }
    }

    private void useCoupon() {
        Member member = identifyMember();
        if (member == null) return;

        String nameCheck = view.input("회원 이름을 입력해주세요. (" + member.getName() + ")");
        if (!member.getName().equals(nameCheck)) {
            view.showMessage("회원 이름이 일치하지 않습니다.");
            return;
        }

        view.showMessage("현재 적립된 쿠폰은 " + member.getCoupons() + "개 입니다.");
        try {
            int use = Integer.parseInt(view.input("사용할 쿠폰 갯수를 입력해주세요."));
            if (use > member.getCoupons()) {
                view.showMessage("쿠폰이 부족합니다.");
                return;
            }
            member.useCoupons(use);
            view.showMessage("쿠폰이 사용되었습니다.");
        } catch (NumberFormatException e) {
            view.showMessage("숫자를 입력해주세요.");
        }
    }

    private Member identifyMember() {
        String suffix = view.input("전화번호 뒤 네자리를 입력해주세요");
        Member member = repository.findFirstByPhoneSuffix(suffix);  // 수정된 부분
        if (member == null) {
            view.showMessage("해당 회원을 찾을 수 없습니다.");
            return null;
        }
        String confirm = view.input(member.getName() + " 회원님 맞으신가요? (Y/N)");
        return confirm.equalsIgnoreCase("Y") ? member : null;
    }

    private boolean isValidPhone(String phone) {
        return phone.matches(PHONE_PATTERN);
    }
}
