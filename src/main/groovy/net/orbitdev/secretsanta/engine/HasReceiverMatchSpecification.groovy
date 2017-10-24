package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.db.ISecretSantaStore
import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.patterns.specification.CompositeSpecification

/**
 * Specifies whether a FamilyMember is a Giver
 */
class HasReceiverMatchSpecification extends CompositeSpecification<FamilyMember> {

    private ISecretSantaStore matchStore

    /**
     * Constructor
     * @param matchStore
     */
    HasReceiverMatchSpecification(ISecretSantaStore matchStore) {
        this.matchStore = matchStore
    }

    /**
     * Test if a family member is in a match as a giver
     * @param familyMember
     * @return True when matched
     */
    @Override
    boolean isSatisfiedBy(FamilyMember familyMember) {
        return matchStore.isGiver(familyMember)
    }
}
