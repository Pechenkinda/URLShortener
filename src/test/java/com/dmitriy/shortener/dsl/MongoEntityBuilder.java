package com.dmitriy.shortener.dsl;

import lombok.RequiredArgsConstructor;
import org.jeasy.random.EasyRandom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class MongoEntityBuilder<T> {

    private final Class<T> type;
    private final MongoRepository<T, String> entityRepository;
    private final EasyRandom random;

    private List<T> entityList = new LinkedList<>();

    public MongoEntityBuilder<T> with(Consumer<T> customizer) {
        var entity = random.nextObject(type);
        customizer.accept(entity);
        entityList.add(entity);
        return this;
    }

    public MongoEntityBuilder<T> withAny() {
        return with(r -> {});
    }

    public MongoEntityBuilder<T> withSome(int count) {
        IntStream.range(0, count).forEach(i -> withAny());
        return this;
    }

    public MongoEntityBuilder<T> withSome(int count, Function<Integer, Consumer<T>> customFunction) {
        IntStream.range(0, count).forEach(i -> with(customFunction.apply(i)));
        return this;
    }

    public List<T> build() {
        return entityRepository.insert(entityList);
    }

    public T buildSingle() {
        if (entityList.size() != 1) {
            throw new IllegalStateException("buildSingle expects one value to be added");
        }
        return build().get(0);
    }
}
