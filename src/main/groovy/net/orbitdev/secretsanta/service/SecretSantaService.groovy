package net.orbitdev.secretsanta.service

import net.orbitdev.secretsanta.net.orbitdev.secretsanta.db.ISecretSantaStore
import net.orbitdev.secretsanta.net.orbitdev.secretsanta.db.IFamilyMemberStore

class SecretSantaService {

    IFamilyMemberStore familyMemberStore
    ISecretSantaStore matchStore

    SecretSantaService(IFamilyMemberStore familyStore, ISecretSantaStore matchStore) {
        this.familyMemberStore = familyStore
        this.matchStore = matchStore
    }

    void generateMatches(){
        familyMemberStore.members.each{
            if(!hasMatch(it.name, MATCH_TYPE.GIVER)){
                matchStore.addMatch(it.name, makeMatch(it.name))
            }
            if(!hasMatch(it.name, MATCH_TYPE.RECEIVER)){
                matchStore.addMatch(makeMatch(it.name),it.name)
            }

        }
    }

    //TODO: This needs to check if the matchName is already matched for the type
    String makeMatch(String matchName) {
        String matchedName = matchName

        while(matchedName == matchName){
            def random = familyMemberStore.getRandomMember()
            matchedName = random.name
        }

        return matchedName
    }

    /**
     * Check to see if there is a match for the name based on the type of match
     * When MATCH_TYPE.GIVER then check to see if there is a matching RECEIVER
     *
     * @param matchName
     * @param matchType
     * @return
     */
    boolean hasMatch(String matchName, MATCH_TYPE matchType){
        if(matchType == MATCH_TYPE.GIVER)
            matchStore.hasReceiver(matchName)
        else
            matchStore.hasGiver(matchName)
    }

    enum MATCH_TYPE {
        GIVER,
        RECEIVER
    }
}
