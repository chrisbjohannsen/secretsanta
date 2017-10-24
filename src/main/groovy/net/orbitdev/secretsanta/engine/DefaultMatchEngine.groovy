package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.db.ISecretSantaStore
import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.patterns.specification.CompositeSpecification
import net.orbitdev.secretsanta.patterns.specification.Specification

class DefaultMatchEngine implements IMatchEngine {

    private Random random = new Random()
    private ISecretSantaStore store
    private Specification<FamilyMember> hasReceiverSpec
    private Specification<FamilyMember> hasGiverSpec

    /**
     * This class implments the matching algorithm.
     * @param store
     */
    DefaultMatchEngine(ISecretSantaStore store) {
        this.store = store
    }

    /**
     * Constructor overload for testability
     * @param store
     * @param hasReceiverSpec
     * @param hasGiverSpec
     */
    DefaultMatchEngine(ISecretSantaStore store, Specification<FamilyMember> hasReceiverSpec,
                       Specification<FamilyMember> hasGiverSpec) {
        this.store = store
        this.hasReceiverSpec = hasReceiverSpec
        this.hasGiverSpec = hasGiverSpec
    }

    /**
     * Picks a randomly selected family member from the list and validates the member against the santastore.
     * If it is already matched, removed it from the list and call recursively until a match is made or the list is
     * exhausted.
     * @param matchFor
     * @param matchType
     * @param memberList
     * @return
     */
    @Override
    FamilyMember findMatch(FamilyMember matchFor,
                           MatchType matchType,
                           List<FamilyMember> memberList,
                           Specification<FamilyMember> timeLimitSpec,
                           Specification<FamilyMember> isFamilyMemberSpec) {

        if (memberList.size() == 0) {
            return null
        }

        FamilyMember familyMember = memberList[random.nextInt(memberList.size())]

        if (!familyMember) {
            throw new Exception("No Members in list")
        }

        switch (matchType) {
            case MatchType.GIVER:
                //validate that the randomly picked familyMember isn't already a receiver
                if (!getHasReceiverSpec().isSatisfiedBy(familyMember)
                        && timeLimitSpec.isSatisfiedBy(matchFor)
                        && !isFamilyMemberSpec.isSatisfiedBy(familyMember)) {
                    return familyMember
                }
                break

            case MatchType.RECEIVER:
                if (!getHasGiverSpec().isSatisfiedBy(familyMember)
                        && timeLimitSpec.isSatisfiedBy(familyMember)
                        && !isFamilyMemberSpec.isSatisfiedBy(familyMember)) {
                    return familyMember
                }
                break
        }

        memberList.removeAll { it.id == familyMember.id } //remove already tested from list
        findMatch(matchFor, matchType, memberList, timeLimitSpec, isFamilyMemberSpec) //call findMatch until we find an unmatched name
    }

    /**
     * Picks a randomly selected family member from the list and validates the member against the santastore.
     * If it is already matched, removed it from the list and call recursively until a match is made or the list is
     * exhausted.
     * @param target
     * @param memberList
     * @param matchSpec
     * @return
     */
    @Override
    FamilyMember findMatch(FamilyMember target, List<FamilyMember> memberList, CompositeSpecification<FamilyMember> matchSpec) {

        if (memberList.size() == 0) {
            return null
        }

        FamilyMember familyMember = memberList[random.nextInt(memberList.size())]

        if (!familyMember) {
            throw new Exception("No Members in list")
        }

        if(matchSpec.isSatisfiedBy(familyMember)){
            return familyMember
        }

        memberList.removeAll { it.id == familyMember.id } //remove already tested from list
        findMatch(target, memberList, matchSpec)
    }
}
