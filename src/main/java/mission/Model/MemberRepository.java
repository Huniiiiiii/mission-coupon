package mission.Model;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class MemberRepository {
    private final Map<String, Member> members = new HashMap<>();

    public void save(Member member) {
        members.put(member.getPhone(), member);
    }

    public Member findByPhoneSuffix(String suffix) {
        for (Member member : members.values()) {
            if (member.getPhone().endsWith(suffix)) {
                return member;
            }
        }
        return null;
    }

    public List<Member> findAllByPhoneSuffix(String suffix) {
        List<Member> result = new ArrayList<>();
        for (Member member : members.values()) {
            if (member.getPhone().endsWith(suffix)) {
                result.add(member);
            }
        }
        return result;
    }

    public boolean exists(String phone) {
        return members.containsKey(phone);
    }
}
