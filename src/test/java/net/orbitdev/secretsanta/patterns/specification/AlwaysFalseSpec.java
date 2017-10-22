package net.orbitdev.secretsanta.patterns.specification;

/**
 * BasicProgrammingTopics.java.${FILE_NAME}
 */
public class AlwaysFalseSpec<Object> extends CompositeSpecification<Object> {
    @Override
    public boolean isSatisfiedBy(java.lang.Object object) {
        return false;
    }
}
