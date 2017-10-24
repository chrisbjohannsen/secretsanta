package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.db.ISecretSantaStore
import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.patterns.specification.CompositeSpecification

/**
 * Specifies whether a FamilyMember is a Receiver
 */
class HasGiverMatchSpecification extends CompositeSpecification<FamilyMember> {

    private ISecretSantaStore matchStore

    /**
     * Constructor
     * @param matchStore
     */
    HasGiverMatchSpecification(ISecretSantaStore matchStore) {
        this.matchStore = matchStore
    }

    /**
     * Test if a member is matched as a receiver
     * @param familyMember
     * @return true when matched
     */
    @Override
    boolean isSatisfiedBy(FamilyMember familyMember) {
        return matchStore.isReceiver(familyMember)
    }
}
