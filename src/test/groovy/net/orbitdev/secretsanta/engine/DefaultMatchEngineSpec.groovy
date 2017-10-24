package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.db.ISecretSantaStore
import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.util.SpecificationTestUtils
import spock.lang.Shared
import spock.lang.Specification
import net.orbitdev.secretsanta.patterns.specification.Specification as MatchSpec

class DefaultMatchEngineSpec extends Specification {

    DefaultMatchEngine engine
    ISecretSantaStore store
    MatchSpec<FamilyMember> canMatchSpec

    @Shared
    def members = []
    def storeData

    void setup() {
        store = Mock(ISecretSantaStore)
        canMatchSpec = Mock(net.orbitdev.secretsanta.patterns.specification.Specification)
        members = SpecificationTestUtils.mockFamilyMembers()
        storeData = SpecificationTestUtils.mockSecretSantaMatchesData()
        engine = new DefaultMatchEngine(store)
    }

    def "return a familyMember when a match is found"() {
        setup:
        def member = new FamilyMember(id: 5)
        store.getMatches(5) >> storeData.get(5)
        canMatchSpec.isSatisfiedBy(_) >> true

        when:
        def result = engine.findMatch(member,  members.toList(), canMatchSpec)

        then:
        result instanceof FamilyMember

    }

    def "returns null when no matches are satisfied"() {
        setup:
        canMatchSpec.isSatisfiedBy(_) >> false
        store.getMatches(5) >> storeData.get(5)

        when:
        def result = engine.findMatch(new FamilyMember(id: 5),[new FamilyMember(id:2, name: "Test")], canMatchSpec)

        then:
        result == null
    }

    def "returns null when no members are passed in are found"() {
        setup:
        canMatchSpec.isSatisfiedBy(_) >> true
        store.getMatches(5) >> null

        when:
        def result = engine.findMatch(new FamilyMember(id: 5),[], canMatchSpec)

        then:
        result == null
    }

}
