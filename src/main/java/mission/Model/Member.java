package mission.Model;

public class Member {
    private final String name;
    private final String phone;
    private int coupons;

    public Member(String name, String phone) {
        this.name = name;
        this.phone = phone;
        this.coupons = 0;
    }

    public String getName() { return name; }

    public String getPhone() { return phone; }

    public int getCoupons() { return coupons; }

    public void addCoupons(int count) {
        if (count < 0) throw new IllegalArgumentException("쿠폰 적립 수는 0 이상이어야 합니다.");
        this.coupons += count;
    }

    public void useCoupons(int count) {
        if (count < 0) throw new IllegalArgumentException("쿠폰 사용 수는 0 이상이어야 합니다.");
        if (count > this.coupons) throw new IllegalArgumentException("보유 쿠폰보다 많이 사용할 수 없습니다.");
        this.coupons -= count;
    }
}
