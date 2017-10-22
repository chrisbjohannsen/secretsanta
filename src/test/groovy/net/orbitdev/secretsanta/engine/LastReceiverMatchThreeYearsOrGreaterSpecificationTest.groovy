package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.db.ISecretSantaStore
import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.util.SpecificationTestUtils
import spock.lang.Specification

class LastReceiverMatchThreeYearsOrGreaterSpecificationTest extends Specification {
    net.orbitdev.secretsanta.patterns.specification.Specification<FamilyMember> specification
    ISecretSantaStore store
    Map matches

    def setup() {
        matches = SpecificationTestUtils.mockSecretSantaMatchesData()
    }

    def "returns true if last match date greater than 3 years"() {
        setup:
        store = Mock()

        def match = matches.get(0)
        store.getMatches(7) >> match
        specification = new LastReceiverMatchThreeYearsOrGreaterSpecification(store)

        when:
        def actual = specification.isSatisfiedBy(match[0].receiver)

        then:
        actual == true

    }

    def "returns true if last match date equal to 3 years"() {
        setup:
        store = Mock()

        def match = matches.get(5)
        store.getMatches(1) >> match
        specification = new LastReceiverMatchThreeYearsOrGreaterSpecification(store)

        when:
        def actual = specification.isSatisfiedBy(match[0].receiver)

        then:
        actual == true

    }

    def "returns false if last match date less than 3 years"() {
        setup:
        store = Mock()
        def match = matches.get(1)
        store.getMatches(2) >> match
        specification = new LastReceiverMatchThreeYearsOrGreaterSpecification(store)

        when:
        def actual = specification.isSatisfiedBy(match[0].receiver)

        then:
        actual == false

    }
}
