package  net.orbitdev.secretsanta.patterns.specification;

import org.junit.Test;

import static org.junit.Assert.*;

public class NotSpecificationTest {

    @Test
    public void isSatisfiedBy() throws Exception {
        AlwaysTrueSpec trueSpec = new AlwaysTrueSpec();
        AlwaysFalseSpec falseSpec = new AlwaysFalseSpec();

        NotSpecification<Object> spec = new NotSpecification<Object>(trueSpec);

        assertFalse(spec.isSatisfiedBy(new Object()));

        spec = new NotSpecification<Object>(falseSpec);
        assertTrue(spec.isSatisfiedBy(new Object()));
    }

}