package ru.job4j.userstorage;

import net.jcip.annotations.ThreadSafe;

import java.util.HashSet;
import java.util.Set;

@ThreadSafe
public final class UserStorage {
    private final Set<User> users = new HashSet<>();

    public synchronized boolean add(User user) {
        boolean rsl = false;
        if (!users.contains(user)) {
            rsl = users.add(user);
        }
        return rsl;
    }

    public synchronized boolean update(User user) {
        boolean rsl = false;
        if (users.contains(user)) {
            rsl = users.remove(user)
                    && users.add(user);
        }
        return rsl;
    }

    public synchronized boolean delete(User user) {
        boolean rsl = false;
        if (users.contains(user)) {
            rsl = users.remove(user);
        }
        return rsl;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        User fromUser = findUserById(fromId);
        User toUser = findUserById(toId);
        if (fromUser != null && toUser != null) {
            fromUser.setAmount(fromUser.getAmount() - amount);
            toUser.setAmount(toUser.getAmount() + amount);
            update(fromUser);
            update(toUser);
            rsl = true;
        }
        return rsl;
    }

    public synchronized User findUserById(int id) {
        User user = null;
        for (User tempUser : users) {
            if (tempUser.getId() == id) {
                user = tempUser;
            }
        }
        return user;
    }
}
