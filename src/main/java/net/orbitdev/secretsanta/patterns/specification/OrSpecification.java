package net.orbitdev.secretsanta.patterns.specification;

public class OrSpecification<T> extends CompositeSpecification<T> {

    private final Specification<T> leftSide;
    private final Specification<T> rightSide;

    public OrSpecification(Specification<T> leftSide, Specification<T> rightSide) {

        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    @Override
    public boolean isSatisfiedBy(final T t) {
        return leftSide.isSatisfiedBy(t) || rightSide.isSatisfiedBy(t);
    }
}
