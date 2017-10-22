package net.orbitdev.secretsanta.service

import net.orbitdev.secretsanta.db.IFamilyMemberStore
import net.orbitdev.secretsanta.db.ISecretSantaStore
import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.engine.IMatchEngine
import net.orbitdev.secretsanta.engine.MatchType

/**
 * Orchestrates the creation of matches and populates the santa store
 */
class SecretSantaService {

    private IFamilyMemberStore familyMemberStore
    private ISecretSantaStore matchStore
    private IMatchEngine matchEngine

    SecretSantaService(IFamilyMemberStore familyStore, ISecretSantaStore matchStore, IMatchEngine matchEngine) {
        this.familyMemberStore = familyStore
        this.matchStore = matchStore
        this.matchEngine = matchEngine

    }

    /**
     * Iterates the family store members and generate both giver and receiver matches for each
     * optimized by not calling the match making code if a match is already made
     */
    void generateMatches(){
        familyMemberStore.members.each{
            def match
            if(!hasMatch(it.name, MatchType.GIVER)){
                match = makeMatch(it, MatchType.RECEIVER)
                if(match)
                    matchStore.addMatch(it, match)
            }
            if(!hasMatch(it.name, MatchType.RECEIVER)){
                match = makeMatch(it,MatchType.GIVER)
                if(match)
                    matchStore.addMatch(match,it)
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

        //clone members to help optimize selection algorithm
        def memberClone = familyMemberStore.getMembers().collect()
        memberClone.removeAll{ it.name == matchName.name } //remove self
        matchEngine.findMatch(matchName, matchType, memberClone)
    }

    /**
     * Check to see if there is a match for the name based on the type of match
     * When MatchType.GIVER then check to see if there is a matching RECEIVER
     * @param matchName
     * @param matchType
     * @return
     */
    private boolean hasMatch(String matchName, MatchType matchType){
        if(matchType == MatchType.GIVER){
            matchStore.isGiver(matchName)
        } else {
            matchStore.isReceiver(matchName)
        }
    }

}
