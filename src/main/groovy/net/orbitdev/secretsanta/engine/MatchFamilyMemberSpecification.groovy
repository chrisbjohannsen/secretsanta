package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.patterns.specification.AndSpecification
import net.orbitdev.secretsanta.patterns.specification.CompositeSpecification

/**
 * This spec tests a family member and returns true if it already has both a giver and receiver match
 */
class MatchFamilyMemberSpecification extends CompositeSpecification<FamilyMember> {

    private HasReceiverMatchSpecification receiverMatchSpecification
    private HasGiverMatchSpecification giverMatchSpecification

    MatchFamilyMemberSpecification(HasReceiverMatchSpecification receiverMatchSpecification, HasGiverMatchSpecification giverMatchSpecification) {
        this.receiverMatchSpecification = receiverMatchSpecification
        this.giverMatchSpecification = giverMatchSpecification
    }

    @Override
    boolean isSatisfiedBy(FamilyMember familyMember) {
        def and = new AndSpecification(receiverMatchSpecification, giverMatchSpecification)
        and.isSatisfiedBy(familyMember)
    }
}
