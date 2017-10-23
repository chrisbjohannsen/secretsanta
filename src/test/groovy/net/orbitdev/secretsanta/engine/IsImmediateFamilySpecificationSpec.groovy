package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.util.SpecificationTestUtils
import spock.lang.Shared
import spock.lang.Specification

class IsImmediateFamilySpecificationSpec extends Specification {

    @Shared
    FamilyMember[] family= SpecificationTestUtils.mockFamilyMembers()
    IsImmediateFamilySpecification spec

    void setup() {
        spec = new IsImmediateFamilySpecification(family[2]) //Ross
    }

    def "returns false if not in immediate family"() {
        expect:
        !spec.isSatisfiedBy(input)

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

    def "returns true when is an in immediate family"() {
        expect:
        spec.isSatisfiedBy(input)

        where:
        input << [
        family[0],
        family[10],
        family[11]
        ]

    }
}
