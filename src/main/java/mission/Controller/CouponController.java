package mission.Controller;

import mission.Model.Member;
import mission.Model.MemberRepository;
import mission.View.View;
import java.util.List;

public class CouponController {
    private final MemberRepository repository = new MemberRepository();
    private final View view = new View();

    public void run() {
        while (true) {
            view.printMenu();
            String choice = view.input("");

            switch (choice) {
                case "1" -> registerMember();
                case "2" -> searchCoupon();
                case "3" -> addCoupon();
                case "4" -> useCoupon();
                case "5" -> {
                    view.showMessage("프로그램을 종료합니다.");
                    return;
                }
                default -> view.showMessage("올바르지 않은 선택입니다.");
            }
        }
    }

    private void registerMember() {
        String name = view.input("이름을 입력해주세요.");
        String phone = view.input("전화번호를 입력해주세요 (ex. 010-1234-1234)");

        if (!phone.matches("^010-\\d{4}-\\d{4}$")) {
            throw new IllegalArgumentException("🚫 잘못된 전화번호 형식입니다. '010-1234-5678' 형식으로 입력해주세요.");
        }

        if (repository.exists(phone)) {
            view.showMessage("이미 등록된 전화번호입니다.");
            return;
        }

        repository.save(new Member(name, phone));
        view.showMessage("회원정보가 등록되었습니다.");
    }


    private void searchCoupon() {
        String suffix = view.input("전화번호 뒤 네자리를 입력해주세요");
        List<Member> foundMembers = repository.findAllByPhoneSuffix(suffix);

        if (foundMembers.isEmpty()) {
            view.showMessage("해당 회원을 찾을 수 없습니다.");
            return;
        }

        if (foundMembers.size() == 1) {
            Member member = foundMembers.get(0);
            view.showMessage(member.getName() + " 회원님의 현재 쿠폰은 " + member.getCoupons() + "개입니다.");
        } else {
            // 이름으로 추가 식별
            StringBuilder names = new StringBuilder();
            for (Member m : foundMembers) {
                names.append(m.getName()).append(", ");
            }
            String nameInput = view.input("이름을 입력해 주세요? (" + names.substring(0, names.length() - 2) + ")");
            for (Member m : foundMembers) {
                if (m.getName().equals(nameInput)) {
                    view.showMessage(m.getName() + " 회원님의 현재 쿠폰은 " + m.getCoupons() + "개입니다.");
                    return;
                }
            }
            view.showMessage("일치하는 이름이 없습니다.");
        }
    }


    private void addCoupon() {
        String suffix = view.input("전화번호 뒤 네자리를 입력해주세요");
        Member member = repository.findByPhoneSuffix(suffix);
        if (member == null) {
            view.showMessage("해당 회원을 찾을 수 없습니다.");
            return;
        }

        String confirm = view.input(member.getName() + " 회원님 맞으신가요? (Y/N)");
        if (!confirm.equalsIgnoreCase("Y")) return;

        view.showMessage("현재 적립된 쿠폰은 " + member.getCoupons() + "개 입니다.");
        int add = Integer.parseInt(view.input("적립할 쿠폰 갯수를 입력해주세요."));
        member.addCoupons(add);
        view.showMessage("쿠폰이 적립되었습니다.");
    }

    private void useCoupon() {
        String suffix = view.input("전화번호 뒤 네자리를 입력해주세요");
        Member member = repository.findByPhoneSuffix(suffix);
        if (member == null) {
            view.showMessage("해당 회원을 찾을 수 없습니다.");
            return;
        }

        String nameCheck = view.input("회원 이름을 입력해주세요. (" + member.getName() + ")");
        if (!member.getName().equals(nameCheck)) {
            view.showMessage("회원 이름이 일치하지 않습니다.");
            return;
        }

        view.showMessage("현재 적립된 쿠폰은 " + member.getCoupons() + "개 입니다.");
        int use = Integer.parseInt(view.input("사용할 쿠폰 갯수를 입력해주세요."));
        if (use > member.getCoupons()) {
            view.showMessage("쿠폰이 부족합니다.");
            return;
        }
        member.useCoupons(use);
        view.showMessage("쿠폰이 사용되었습니다.");
    }
}
