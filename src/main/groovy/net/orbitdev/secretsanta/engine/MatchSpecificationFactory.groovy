package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.db.ISecretSantaStore
import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.patterns.specification.CompositeSpecification
import net.orbitdev.secretsanta.patterns.specification.Specification

/**
 * Factory to generate specifications
 */
class MatchSpecificationFactory {

    /**
     * create a HasGiverMatchSpecification and inject the passed in store
     * @param store
     * @return
     */
    static Specification<FamilyMember> hasGiver(ISecretSantaStore store){
        return new HasGiverMatchSpecification(store)
    }

    /**
     * create a HasReceiverMatchSpecification and inject the passed in store
     * @param store
     * @return
     */
    static Specification<FamilyMember> hasReceiver(ISecretSantaStore store){
        return new HasReceiverMatchSpecification(store)
    }

    /**
     * creates a composite specification to evaluate whether FamilyMembers have been matched to recently
     */
    static Specification<FamilyMember> timeLimitExpired(ISecretSantaStore store){
        return new LastGiverMatchThreeYearsOrGreaterSpecification(store)
                .and(new LastReceiverMatchThreeYearsOrGreaterSpecification(store))
    }
}
