package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.db.ISecretSantaStore
import net.orbitdev.secretsanta.domain.FamilyMember
import spock.lang.Shared
import spock.lang.Specification

class DefaultMatchEngineSpec extends Specification {

    DefaultMatchEngine engine
    def giverSpec
    def receiverSpec
    def store

    @Shared
    def members = []

    void setup() {
        store = Mock(ISecretSantaStore)
        giverSpec = Mock(net.orbitdev.secretsanta.patterns.specification.Specification)
        receiverSpec = Mock(net.orbitdev.secretsanta.patterns.specification.Specification)

        members = [new FamilyMember(id: 1, name: 'Lena'),
                   new FamilyMember(id: 2, name: 'Frida'),
                   new FamilyMember(id: 3, name: 'Yamato'),
                   new FamilyMember(id: 4, name: 'Orbit')]

        engine = new DefaultMatchEngine(store, receiverSpec, giverSpec)
    }

    def "return a familyMember when no giver match found"() {
        setup:
        giverSpec.isSatisfiedBy(_) >> false

        when:
        def result = engine.findMatch(new FamilyMember(id:5, name:'Ollie'), MatchType.RECEIVER, members )

        then:
        result instanceof FamilyMember

    }

    def "return a familyMember when no receiver match found"() {
        setup:
        receiverSpec.isSatisfiedBy(_) >> false

        when:
        def result = engine.findMatch(new FamilyMember(id:5, name:'Ollie'), MatchType.GIVER, members )

        then:
        result instanceof FamilyMember

    }

    def "returns null when no receiver matches are found"(){
        setup:
        receiverSpec.isSatisfiedBy(_) >> true
        giverSpec.isSatisfiedBy(_) >> true

        when:
        def result = engine.findMatch(new FamilyMember(id:5, name:'Ollie'), MatchType.GIVER, members )

        then:
        result == null
    }

    def "returns null when no giver matches are found"(){
        setup:
        receiverSpec.isSatisfiedBy(_) >> true
        giverSpec.isSatisfiedBy(_) >> true

        when:
        def result = engine.findMatch(new FamilyMember(id:5, name:'Ollie'), MatchType.RECEIVER, members )

        then:
        result == null
    }
    
}
