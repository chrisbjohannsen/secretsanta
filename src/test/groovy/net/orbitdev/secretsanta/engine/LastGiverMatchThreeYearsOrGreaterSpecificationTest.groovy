package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.db.ISecretSantaStore
import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.patterns.specification.Specification as MatchSpec
import net.orbitdev.secretsanta.util.SpecificationTestUtils
import spock.lang.Specification

class LastGiverMatchThreeYearsOrGreaterSpecificationTest extends Specification {

    MatchSpec<FamilyMember> specification
    ISecretSantaStore store
    Map matches

    def setup() {
       matches = SpecificationTestUtils.mockSecretSantaMatchesData()
    }

    def "returns true if last match date greater than 3 years"() {
        setup:
        store = Mock()

        def match = matches.get(0)
        store.getMatches(0) >> match
        specification = new LastGiverMatchThreeYearsOrGreaterSpecification(store)

        when:
        def actual = specification.isSatisfiedBy(match[0].giver)

        then:
        actual == true

    }

    def "returns true if last match date equal to 3 years"() {
        setup:
        store = Mock()

        def match = matches.get(5)
        store.getMatches(5) >> match
        specification = new LastGiverMatchThreeYearsOrGreaterSpecification(store)

        when:
        def actual = specification.isSatisfiedBy(match[0].giver)

        then:
        actual == true

    }

    def "returns false if last match date less than 3 years"() {
        setup:
        store = Mock()
        def match = matches.get(1)
        store.getMatches(1) >> match
        specification = new LastGiverMatchThreeYearsOrGreaterSpecification(store)

        when:
        def actual = specification.isSatisfiedBy(match[0].giver)

        then:
        actual == false

    }
}
