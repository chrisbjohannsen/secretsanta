package net.orbitdev.secretsanta.service

import net.orbitdev.secretsanta.db.IFamilyMemberStore
import net.orbitdev.secretsanta.db.ISecretSantaStore
import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.engine.IMatchEngine
import net.orbitdev.secretsanta.engine.IsImmediateFamilySpecification
import net.orbitdev.secretsanta.engine.LastGiverMatchThreeYearsOrGreaterSpecification
import net.orbitdev.secretsanta.engine.LastReceiverMatchThreeYearsOrGreaterSpecification
import net.orbitdev.secretsanta.engine.MatchType
import net.orbitdev.secretsanta.patterns.specification.Specification

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
            if(!hasMatch(it, MatchType.GIVER)){
                match = makeMatch(it, MatchType.RECEIVER)
                if(match)
                    matchStore.addMatch(it, match)
            }
            if(!hasMatch(it, MatchType.RECEIVER)){
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
     * @param targetMember
     * @return name of the match
     */
    private FamilyMember makeMatch(FamilyMember targetMember, MatchType matchType) {

        //clone members to help optimize selection algorithm
        def memberClone = familyMemberStore.getMembers().collect()

        //remove target from available members
        memberClone.removeAll{ it.id == targetMember.id }

        Specification<FamilyMember> timeLimitSpec = (matchType == MatchType.GIVER) ?
                new LastReceiverMatchThreeYearsOrGreaterSpecification(matchStore, targetMember) :
                new LastGiverMatchThreeYearsOrGreaterSpecification(matchStore, targetMember)

        Specification<FamilyMember> isFamilyMemberSpec = new IsImmediateFamilySpecification(targetMember)

        matchEngine.findMatch(targetMember, matchType, memberClone, timeLimitSpec, isFamilyMemberSpec)
    }

    /**
     * Check to see if there is a match for the name based on the type of match
     * When MatchType.GIVER then check to see if there is a matching RECEIVER
     * @param targetMember
     * @param matchType
     * @return
     */
    private boolean hasMatch(FamilyMember targetMember, MatchType matchType){
        if(matchType == MatchType.GIVER){
            matchStore.isGiver(targetMember)
        } else {
            matchStore.isReceiver(targetMember)
        }
    }

}
