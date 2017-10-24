package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.db.ISecretSantaStore
import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.patterns.specification.CompositeSpecification
import net.orbitdev.secretsanta.patterns.specification.Specification

/**
 * Specification defines collected business rules to determine if a member is a match for a target member
 */
class MatchFamilyMemberSpecification extends CompositeSpecification<FamilyMember> {

    private Specification<FamilyMember> timeLimitSpec
    private Specification<FamilyMember> hasMatchSpec
    private Specification<FamilyMember> isFamilyMemberSpec

    /**
     * Constructor
     * @param matchType the type of match to test the family member for
     * @param target the Family Member to test against
     * @param store a ISecretSantaStore
     */
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

    /**
     * Executes the specs and returns the combined result
     * @param familyMember
     * @return True if all specs are satisfied
     */
    @Override
    boolean isSatisfiedBy(FamilyMember familyMember) {
        this.timeLimitSpec.isSatisfiedBy(familyMember) &&
                !this.isFamilyMemberSpec.isSatisfiedBy(familyMember) &&
                !this.hasMatchSpec.isSatisfiedBy(familyMember)
    }
}
