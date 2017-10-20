package net.orbitdev.secretsanta.patterns.specification;

public interface Specification<T> {

    boolean isSatisfiedBy(T target);

    Specification<T> and(Specification<T> specification);

    Specification<T> or(Specification<T> specification);

    Specification<T> not(Specification<T> specification);
}
