package net.orbitdev.secretsanta.service

import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.net.orbitdev.secretsanta.db.ISecretSantaStore
import net.orbitdev.secretsanta.net.orbitdev.secretsanta.db.IFamilyMemberStore

class SecretSantaService {

    private IFamilyMemberStore familyMemberStore
    private ISecretSantaStore matchStore

    SecretSantaService(IFamilyMemberStore familyStore, ISecretSantaStore matchStore) {
        this.familyMemberStore = familyStore
        this.matchStore = matchStore
    }

    void generateMatches(){
        familyMemberStore.members.each{
            if(!hasMatch(it.name, MATCH_TYPE.GIVER)){
                matchStore.addMatch(it.name, makeMatch(it.name, MATCH_TYPE.RECEIVER))
            }
            if(!hasMatch(it.name, MATCH_TYPE.RECEIVER)){
                matchStore.addMatch(makeMatch(it.name,MATCH_TYPE.GIVER),it.name)
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
    private String makeMatch(String matchName, MATCH_TYPE matchType) {
        Closure findMatch
        findMatch = { String name, MATCH_TYPE type ->

            FamilyMember random = familyMemberStore.getRandomMember()
            if(!random){
                throw new Exception("No Members in store")
            }

            if(!hasMatch(random.name,type) && random.name != name) {
                return random.name
            }
            findMatch(name, matchType) //call findMatch until we find an unmatched name

        }

        findMatch(matchName, matchType)
    }

    /**
     * Check to see if there is a match for the name based on the type of match
     * When MATCH_TYPE.GIVER then check to see if there is a matching RECEIVER
     *
     * @param matchName
     * @param matchType
     * @return
     */
    private boolean hasMatch(String matchName, MATCH_TYPE matchType){
        if(matchType == MATCH_TYPE.GIVER){
            matchStore.hasReceiver(matchName)
        } else {
            matchStore.hasGiver(matchName)
        }

    }

    private enum MATCH_TYPE {
        GIVER,
        RECEIVER
    }
}
