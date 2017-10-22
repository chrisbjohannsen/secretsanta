package  net.orbitdev.secretsanta.patterns.specification;

import org.junit.Test;

import static org.junit.Assert.*;

public class OrSpecificationTest {

    @Test
    public void isSatisfiedBy() throws Exception {

        AlwaysTrueSpec trueSpec = new AlwaysTrueSpec();
        AlwaysFalseSpec falseSpec = new AlwaysFalseSpec();

        OrSpecification<Object> spec = new OrSpecification<Object>(trueSpec, trueSpec);

        assertTrue(spec.isSatisfiedBy(new Object()));

        spec = new OrSpecification<Object>(trueSpec, falseSpec);
        assertTrue(spec.isSatisfiedBy(new Object()));

        spec = new OrSpecification<Object>(falseSpec, falseSpec);
        assertFalse(spec.isSatisfiedBy(new Object()));
    }

}