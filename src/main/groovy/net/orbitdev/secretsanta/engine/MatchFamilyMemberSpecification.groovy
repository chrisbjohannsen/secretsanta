package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.patterns.specification.CompositeSpecification

class MatchFamilyMemberSpecification extends CompositeSpecification<FamilyMember> {

    private HasReceiverMatchSpecification receiverMatchSpecification
    private HasGiverMatchSpecification giverMatchSpecification

    MatchFamilyMemberSpecification(HasReceiverMatchSpecification receiverMatchSpecification, HasGiverMatchSpecification giverMatchSpecification) {
        this.receiverMatchSpecification = receiverMatchSpecification
        this.giverMatchSpecification = giverMatchSpecification
    }

    @Override
    boolean isSatisfiedBy(FamilyMember familyMember) {
        receiverMatchSpecification.isSatisfiedBy(familyMember) && giverMatchSpecification.isSatisfiedBy(familyMember)
    }
}
