package net.orbitdev.secretsanta.engine

import net.orbitdev.secretsanta.db.ISecretSantaStore
import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.patterns.specification.Specification

class DefaultMatchEngine implements IMatchEngine {

    private Random random= new Random()
    private ISecretSantaStore store

    Specification<FamilyMember> getHasRecieverSpec() {
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
    private Specification<FamilyMember> hasReceiverSpec
    private Specification<FamilyMember> hasGiverSpec

    DefaultMatchEngine(ISecretSantaStore store) {
        this.store = store
    }

    DefaultMatchEngine(ISecretSantaStore store, Specification<FamilyMember> hasReceiverSpec, Specification<FamilyMember> hasGiverSpec) {
        this.store = store
        this.hasReceiverSpec = hasReceiverSpec
        this.hasGiverSpec = hasGiverSpec
    }

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
                if(! getHasRecieverSpec().isSatisfiedBy(familyMember) ){
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
