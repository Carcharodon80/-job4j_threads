package ru.job4j.userstorage;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserStorageTest {
    @Test
    public void whenAddUser() {
        UserStorage userStorage = new UserStorage();
        User user = new User(5, 200);
        userStorage.add(user);
        assertEquals(userStorage.findUserById(5), user);
    }

    @Test
    public void whenDeleteUser() {
        UserStorage userStorage = new UserStorage();
        User user = new User(5, 200);
        userStorage.add(user);
        userStorage.delete(user);
        assertNull(userStorage.findUserById(5));
    }

    @Test
    public void whenTransfer() {
        UserStorage userStorage = new UserStorage();
        User user1 = new User(5, 200);
        User user2 = new User(1, 500);
        userStorage.add(user1);
        userStorage.add(user2);
        userStorage.transfer(5, 1, 100);
        assertEquals(100, user1.getAmount());
        assertEquals(600, user2.getAmount());
    }
}