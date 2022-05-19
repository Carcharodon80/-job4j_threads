package ru.job4j.userstorage;

import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public final class UserStorage {
    private final Map<Integer, User> users = new HashMap<>();

    public synchronized boolean add(User user) {
        boolean rsl = false;
        if (!users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            rsl = true;
        }
        return rsl;
    }

    public synchronized boolean update(User user) {
        boolean rsl = false;
        if (users.containsKey(user.getId())) {
            users.remove(user.getId());
            users.put(user.getId(), user);
            rsl = true;
        }
        return rsl;
    }

    public synchronized boolean delete(User user) {
        boolean rsl = false;
        if (users.containsKey(user.getId())) {
            users.remove(user.getId());
            rsl = true;
        }
        return rsl;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        User fromUser = users.get(fromId);
        User toUser = users.get(toId);
        if (fromUser != null && toUser != null) {
            fromUser.setAmount(fromUser.getAmount() - amount);
            toUser.setAmount(toUser.getAmount() + amount);
            update(fromUser);
            update(toUser);
            rsl = true;
        }
        return rsl;
    }
}
