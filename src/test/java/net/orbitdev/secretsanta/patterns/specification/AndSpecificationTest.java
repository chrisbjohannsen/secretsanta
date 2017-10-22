package  net.orbitdev.secretsanta.patterns.specification;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AndSpecificationTest {

    @Test
    public void isSatisfiedBy() throws Exception {

        AlwaysTrueSpec trueSpec = new AlwaysTrueSpec();
        AlwaysFalseSpec falseSpec = new AlwaysFalseSpec();

        AndSpecification<Object> spec = new AndSpecification<Object>(trueSpec, trueSpec);

        assertTrue(spec.isSatisfiedBy(new Object()));

        spec = new AndSpecification<Object>(trueSpec, falseSpec);
        assertFalse(spec.isSatisfiedBy(new Object()));

        spec = new AndSpecification<Object>(falseSpec, falseSpec);
        assertFalse(spec.isSatisfiedBy(new Object()));

    }

}