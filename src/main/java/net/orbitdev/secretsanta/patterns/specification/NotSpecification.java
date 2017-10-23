package net.orbitdev.secretsanta.patterns.specification;

public class NotSpecification<T> extends CompositeSpecification<T> {

    private final Specification<T> specA;

    public NotSpecification(Specification<T> specA) {
        this.specA = specA;
    }

    @Override
    public boolean isSatisfiedBy(final T t) {

        return !this.specA.isSatisfiedBy(t);
    }
}
