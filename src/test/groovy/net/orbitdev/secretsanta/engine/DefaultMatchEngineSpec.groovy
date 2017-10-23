package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.db.ISecretSantaStore
import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.util.SpecificationTestUtils
import spock.lang.Shared
import spock.lang.Specification
import net.orbitdev.secretsanta.patterns.specification.Specification as MatchSpec

class DefaultMatchEngineSpec extends Specification {

    DefaultMatchEngine engine
    MatchSpec<FamilyMember> giverSpec
    MatchSpec<FamilyMember> receiverSpec
    ISecretSantaStore store
    MatchSpec<FamilyMember> timeLimitSpec

    @Shared
    def members = []
    def storeData

    void setup() {
        store = Mock(ISecretSantaStore)
        giverSpec = Mock(net.orbitdev.secretsanta.patterns.specification.Specification)
        receiverSpec = Mock(net.orbitdev.secretsanta.patterns.specification.Specification)
        timeLimitSpec = Mock(net.orbitdev.secretsanta.patterns.specification.Specification)
        members = SpecificationTestUtils.mockFamilyMembers()
        storeData = SpecificationTestUtils.mockSecretSantaMatchesData()

        engine = new DefaultMatchEngine(store, receiverSpec, giverSpec)
    }

    def "return a familyMember when no giver match found"() {
        setup:
        giverSpec.isSatisfiedBy(_) >> false
        store.getMatches(5) >> null
        timeLimitSpec.isSatisfiedBy(_) >> true

        when:
        def result = engine.findMatch(new FamilyMember(id: 5), MatchType.RECEIVER, members.toList(), timeLimitSpec)

        then:
        result instanceof FamilyMember

    }

    def "return a familyMember when no receiver match found"() {
        setup:
        receiverSpec.isSatisfiedBy(_) >> false
        timeLimitSpec.isSatisfiedBy(_) >> true
        store.getMatches(5) >> null

        when:
        def result = engine.findMatch(new FamilyMember(id: 5), MatchType.GIVER, members.toList(), timeLimitSpec)

        then:
        result instanceof FamilyMember

    }

    def "returns null when no receiver matches are found"() {
        setup:
        receiverSpec.isSatisfiedBy(_) >> true
        giverSpec.isSatisfiedBy(_) >> true
        timeLimitSpec.isSatisfiedBy(_) >> true

        when:
        def result = engine.findMatch(new FamilyMember(id: 5), MatchType.GIVER, members.toList(), timeLimitSpec)

        then:
        result == null
    }

    def "returns null when no giver matches are found"() {
        setup:
        receiverSpec.isSatisfiedBy(_) >> true
        giverSpec.isSatisfiedBy(_) >> true
        timeLimitSpec.isSatisfiedBy(_) >> true

        when:
        def result = engine.findMatch(new FamilyMember(id: 5), MatchType.RECEIVER, members.toList(), timeLimitSpec)

        then:
        result == null
    }

    def "returns null when subject has been a giver in the past 3 years"() {
        setup:
        receiverSpec.isSatisfiedBy(_) >> false
        giverSpec.isSatisfiedBy(_) >> false
        timeLimitSpec.isSatisfiedBy(_) >> false

        when:
        def result = engine.findMatch(new FamilyMember(id: 5), MatchType.RECEIVER, members.toList(), timeLimitSpec)

        then:
        result == null
    }

    def "returns null when subject has been a receiver in the past 3 years"() {
        setup:
        receiverSpec.isSatisfiedBy(_) >> false
        giverSpec.isSatisfiedBy(_) >> false
        timeLimitSpec.isSatisfiedBy(_) >> false

        when:
        def result = engine.findMatch(new FamilyMember(id: 5), MatchType.GIVER, members.toList(), timeLimitSpec)

        then:
        result == null
    }
}
