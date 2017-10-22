package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.db.ISecretSantaStore
import net.orbitdev.secretsanta.domain.FamilyMember
import spock.lang.Shared
import spock.lang.Specification

class MatchFamilyMemberSpecificationSpecification extends Specification {

    @Shared
    ISecretSantaStore store

    void setup(){
        store = Mock(ISecretSantaStore)
    }

    void "false when not a giver"() {
        setup:
        store.isGiver(_) >> true
        store.isReceiver(_) >> false

        when:
        def combined = new MatchFamilyMemberSpecification( new HasReceiverMatchSpecification(store), new HasGiverMatchSpecification(store) )

        def result = combined.isSatisfiedBy(new FamilyMember(id: 1, name: 'Lena'))

        then:
        !result
    }

    void "false when not a receiver"() {
        setup:
        store.isGiver(_) >> false
        store.isReceiver(_) >> true

        when:
        def combined = new MatchFamilyMemberSpecification( new HasReceiverMatchSpecification(store), new HasGiverMatchSpecification(store) )

        def result = combined.isSatisfiedBy(new FamilyMember(id: 1, name: 'Lena'))

        then:
        !result
    }

    void "true when both a giver and receiver"() {
        setup:
        store.isGiver(_) >> true
        store.isReceiver(_) >> true

        when:
        def combined = new MatchFamilyMemberSpecification( new HasReceiverMatchSpecification(store), new HasGiverMatchSpecification(store) )

        def result = combined.isSatisfiedBy(new FamilyMember(id: 1, name: 'Lena'))

        then:
        result
    }
}
