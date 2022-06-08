package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    /**
     * putIfAbsent() вернет null, если такой ключ есть в map.
     */
    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    /**
     * computeIfPresent - ищет по ключу, и если находит - делает что-то со значением
     * выкидывает исключение, если версии не равны
     */
    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(),
                (key, value) -> {
                    if (model.getId() != value.getId()) {
                        throw new OptimisticException("Versions are not equal.");
                    }
                    Base newBase = new Base(model.getId(), model.getVersion() + 1);
                    newBase.setName(model.getName());
                    return newBase;
                }
        ) != null;
    }

    /**
     * remove(key, value) - удаляет, если под этим ключом лежит это значение
     */
    public boolean delete(Base model) {
        return memory.remove(model.getId(), model);
    }
}
