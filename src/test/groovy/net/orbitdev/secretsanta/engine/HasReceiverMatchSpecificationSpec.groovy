package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.db.ISecretSantaStore
import net.orbitdev.secretsanta.domain.FamilyMember
import spock.lang.Specification

class HasReceiverMatchSpecificationSpec extends Specification {

    def store
    net.orbitdev.secretsanta.patterns.specification.Specification<FamilyMember> spec

    def "returns false when not in store"() {
        setup:
        store = Mock(ISecretSantaStore)
        store.isGiver(_) >> false
        spec = new HasReceiverMatchSpecification(store)

        when:
        def member = new FamilyMember(id: 0, name: 'Adolpho')
        def actual = spec.isSatisfiedBy(member)

        then:
        actual == false
    }

    def "returns true when in store"() {
        setup:
        store = Mock(ISecretSantaStore)
        store.isGiver(_) >> true
        spec = new HasReceiverMatchSpecification(store)

        when:
        def member = new FamilyMember(id: 0, name: 'Adolpho')
        def actual = spec.isSatisfiedBy(member)

        then:
        actual == true
    }
}
