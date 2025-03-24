package mission.Model;

public class Member {
    private String name;
    private String phone;
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
        this.coupons += count;
    }

    public void useCoupons(int count) {
        this.coupons -= count;
    }
}
