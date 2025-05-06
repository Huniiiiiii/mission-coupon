package mission.Model;

import java.util.*;
import java.util.stream.Collectors;

public class MemberRepository {
    private final Map<String, Member> members = new HashMap<>();

    public void save(Member member) {
        members.put(member.getPhone(), member);
    }

    public Member findFirstByPhoneSuffix(String suffix) {
        return members.values().stream()
                .filter(m -> m.getPhone().endsWith(suffix))
                .findFirst()
                .orElse(null);
    }

    public List<Member> findAllByPhoneSuffix(String suffix) {
        return members.values().stream()
                .filter(m -> m.getPhone().endsWith(suffix))
                .collect(Collectors.toList());
    }

    public boolean exists(String phone) {
        return members.containsKey(phone);
    }
}
