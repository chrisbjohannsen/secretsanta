package net.orbitdev.secretsanta.patterns.specification;

public abstract class CompositeSpecification<T> implements Specification<T> {

    public abstract boolean isSatisfiedBy(T t);

    @Override
    public Specification<T> and(final Specification<T> specification) {
        return new AndSpecification<T>(this,specification);
    }

    @Override
    public Specification<T> or(final Specification<T> specification){
        return new OrSpecification<T>(this, specification);
    }

    @Override
    public Specification<T> not(final Specification<T> specification){
        return new NotSpecification<T>(specification);
    }
}
