package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.db.ISecretSantaStore
import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.patterns.specification.CompositeSpecification

/**
 * Specifies whether a FamilyMember is a Receiver
 */
class HasGiverMatchSpecification extends CompositeSpecification<FamilyMember> {

    private ISecretSantaStore matchStore

    HasGiverMatchSpecification(ISecretSantaStore matchStore) {
        this.matchStore = matchStore
    }

    @Override
    boolean isSatisfiedBy(FamilyMember familyMember) {
        return matchStore.isReceiver(familyMember.name)
    }
}
