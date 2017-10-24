package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.db.ISecretSantaStore
import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.patterns.specification.CompositeSpecification
import net.orbitdev.secretsanta.patterns.specification.Specification

/**
 * This spec tests a family member and returns true if it already has both a giver and receiver match
 */
class MatchFamilyMemberSpecification extends CompositeSpecification<FamilyMember> {

    private Specification<FamilyMember> timeLimitSpec
    private Specification<FamilyMember> hasMatchSpec
    private Specification<FamilyMember> isFamilyMemberSpec

    MatchFamilyMemberSpecification(MatchType matchType, FamilyMember target, ISecretSantaStore store) {
        if (matchType == MatchType.GIVER) {
            timeLimitSpec = new LastReceiverMatchThreeYearsOrGreaterSpecification(store, target)
            hasMatchSpec = new HasReceiverMatchSpecification(store)
            isFamilyMemberSpec = new IsImmediateFamilySpecification(target)
        }

        if (matchType == MatchType.RECEIVER) {
            timeLimitSpec = new LastReceiverMatchThreeYearsOrGreaterSpecification(store, target)
            hasMatchSpec = new HasGiverMatchSpecification(store)
            isFamilyMemberSpec = new IsImmediateFamilySpecification(target)
        }
    }

    @Override
    boolean isSatisfiedBy(FamilyMember familyMember) {
        this.timeLimitSpec.isSatisfiedBy(familyMember) &&
                !this.isFamilyMemberSpec.isSatisfiedBy(familyMember) &&
                !this.hasMatchSpec.isSatisfiedBy(familyMember)
    }
}
