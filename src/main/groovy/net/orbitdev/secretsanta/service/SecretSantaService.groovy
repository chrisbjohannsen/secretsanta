package net.orbitdev.secretsanta.service

import net.orbitdev.secretsanta.db.ISecretSantaStore
import net.orbitdev.secretsanta.db.IFamilyMemberStore
import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.engine.IMatchEngine
import net.orbitdev.secretsanta.engine.MatchType

class SecretSantaService {

    private IFamilyMemberStore familyMemberStore
    private ISecretSantaStore matchStore
    private IMatchEngine matchEngine

    SecretSantaService(IFamilyMemberStore familyStore, ISecretSantaStore matchStore, IMatchEngine matchEngine) {
        this.familyMemberStore = familyStore
        this.matchStore = matchStore
        this.matchEngine = matchEngine
    }

    void generateMatches(){
        familyMemberStore.members.each{
            if(!hasMatch(it.name, MatchType.GIVER)){
                matchStore.addMatch(it, makeMatch(it, MatchType.RECEIVER))
            }
            if(!hasMatch(it.name, MatchType.RECEIVER)){
                matchStore.addMatch(makeMatch(it,MatchType.GIVER),it)
            }

        }
    }

    /**
     * Get a random name from the members store that isn't the passed in name
     * Make sure the random name is not already in a match set for the particular match
     * type.
     * @param matchName
     * @return name of the match
     */
    private FamilyMember makeMatch(FamilyMember matchName, MatchType matchType) {

        //clone members to help optimize randomizing
        def memberClone = familyMemberStore.getMembers().collect()
        memberClone.removeAll{ it.name == matchName.name } //remove self
        matchEngine.findMatch(matchName, matchType, memberClone)
    }

//    private String findMatch(String matchFor, MatchType matchType, List<FamilyMember> memberList){
//        Random randomizer = new Random()
//        FamilyMember random = memberList[randomizer.nextInt(memberList.size())]
//        if(!random){
//            throw new Exception("No Members in store")
//        }
//
//        if(!hasMatch(random.name,matchType)) {
//            return random.name
//        }
//
//        memberList.removeAll{ it.name == random.name } //remove already tested from list
//        findMatch(matchFor, matchType, memberList) //call findMatch until we find an unmatched name
//    }

    /**
     * Check to see if there is a match for the name based on the type of match
     * When MatchType.GIVER then check to see if there is a matching RECEIVER
     *
     * @param matchName
     * @param matchType
     * @return
     */
    private boolean hasMatch(String matchName, MatchType matchType){
        if(matchType == MatchType.GIVER){
            matchStore.hasReceiver(matchName)
        } else {
            matchStore.hasGiver(matchName)
        }

    }

}
