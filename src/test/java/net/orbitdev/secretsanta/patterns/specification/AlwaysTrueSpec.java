package net.orbitdev.secretsanta.patterns.specification;

public class AlwaysTrueSpec<Object> extends CompositeSpecification<Object> {
    @Override
    public boolean isSatisfiedBy(java.lang.Object object) {
        return true;
    }
}
