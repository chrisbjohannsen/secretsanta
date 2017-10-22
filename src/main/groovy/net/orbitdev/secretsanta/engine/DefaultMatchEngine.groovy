package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.db.ISecretSantaStore
import net.orbitdev.secretsanta.domain.FamilyMember
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

    Specification<FamilyMember> getHasReceiverSpec() {
        if(!hasReceiverSpec) {
            hasReceiverSpec = MatchSpecificationFactory.hasReceiver(store)
        }
        return hasReceiverSpec
    }

    Specification<FamilyMember> getHasGiverSpec() {
        if(!hasGiverSpec){
            hasGiverSpec = MatchSpecificationFactory.hasGiver(store)
        }
        return hasGiverSpec
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
    FamilyMember findMatch(FamilyMember matchFor, MatchType matchType, List<FamilyMember> memberList) {

        if(memberList.size() == 0){
            return null
        }

        FamilyMember familyMember = memberList[random.nextInt(memberList.size())]

        if(!familyMember){
            throw new Exception("No Members in list")
        }

        switch (matchType){

            case MatchType.GIVER:
                //validate that the randomly picked familyMember isn't already a receiver
                if(! getHasReceiverSpec().isSatisfiedBy(familyMember) ){
                    return familyMember
                }
                break

            case MatchType.RECEIVER:
                if(! getHasGiverSpec().isSatisfiedBy(familyMember)){
                    return familyMember
                }
                break
        }

        memberList.removeAll{ it.name == familyMember.name } //remove already tested from list
        findMatch(matchFor, matchType, memberList) //call findMatch until we find an unmatched name
    }
}
