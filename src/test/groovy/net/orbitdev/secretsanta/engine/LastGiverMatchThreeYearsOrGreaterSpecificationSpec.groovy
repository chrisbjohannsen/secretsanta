package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.db.ISecretSantaStore
import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.patterns.specification.Specification as MatchSpec
import net.orbitdev.secretsanta.util.SpecificationTestUtils
import spock.lang.Specification

class LastGiverMatchThreeYearsOrGreaterSpecificationSpec extends Specification {

    MatchSpec<FamilyMember> specification
    ISecretSantaStore store
    Map matches

    def setup() {
       matches = SpecificationTestUtils.mockSecretSantaMatchesData()
    }

    def "returns true if last match date greater than 3 years"() {
        setup:
        store = Mock()

        def match = matches.get(3)
        store.getMatches(3) >> match
        specification = new LastGiverMatchThreeYearsOrGreaterSpecification(store,match[0].receiver)

        when:
        def actual = specification.isSatisfiedBy(match[0].giver)

        then:
        actual

    }

    def "returns true if last match date equal to 3 years"() {
        setup:
        store = Mock()

        def match = matches.get(5)
        store.getMatches(5) >> match
        specification = new LastGiverMatchThreeYearsOrGreaterSpecification(store,match[0].receiver)

        when:
        def actual = specification.isSatisfiedBy(match[0].giver)

        then:
        actual

    }

    def "returns false if last match date less than 3 years"() {
        setup:
        store = Mock()
        def match = matches.get(1)
        store.getMatches(1) >> match
        specification = new LastGiverMatchThreeYearsOrGreaterSpecification(store,match[0].receiver)

        when:
        def actual = specification.isSatisfiedBy(match[0].giver)

        then:
        !actual

    }

    def "returns true if not matched"(){
        setup:
        store = Mock()
        def match = matches.get(2)
        store.getMatches(2) >> match
        specification = new LastGiverMatchThreeYearsOrGreaterSpecification(store,match[0].receiver)

        when:
        def actual = specification.isSatisfiedBy(new FamilyMember(id:23,name:'Erik'))

        then:
        actual
    }

    def "returns false when latest match is less than 3 years"(){
        setup:
        store = Mock()
        def match = matches.get(4)
        store.getMatches(4) >> match
        specification = new LastGiverMatchThreeYearsOrGreaterSpecification(store, match[0].receiver)

        when:
        def actual = specification.isSatisfiedBy(match[0].giver)

        then:
        !actual
    }
}
