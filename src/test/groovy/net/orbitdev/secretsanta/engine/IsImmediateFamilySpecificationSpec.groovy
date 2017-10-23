package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.util.SpecificationTestUtils
import spock.lang.Shared
import spock.lang.Specification

class IsImmediateFamilySpecificationSpec extends Specification {

    @Shared
    FamilyMember[] family
    IsImmediateFamilySpecification spec

    void setup() {

        family = SpecificationTestUtils.mockFamilyMembers()
        spec = new IsImmediateFamilySpecification(family[2]) //Ross
    }

    def "returns false if not in immediate family"() {
        setup:
        family = SpecificationTestUtils.mockFamilyMembers()

        expect:
        spec.isSatisfiedBy(input) == false

        where:
        input << [
        family[1],
        family[3],
        family[5],
        family[6],
        family[7],
        family[8],
        family[9]]

    }
}
