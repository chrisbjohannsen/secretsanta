package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.db.ISecretSantaStore
import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.patterns.specification.Specification

class MatchSpecificationFactory {

    static Specification<FamilyMember> hasGiver(ISecretSantaStore store){
        return new HasGiverMatchSpecification(store)
    }

    static Specification<FamilyMember> hasReceiver(ISecretSantaStore store){
        return new HasReceiverMatchSpecification(store)
    }
}
