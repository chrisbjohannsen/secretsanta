package net.orbitdev.secretsanta.db

import net.orbitdev.secretsanta.domain.FamilyMember

class InMemorySecretSantaStore implements ISecretSantaStore {

    Map matches = [:]

    @Override
    void addMatch(FamilyMember giver, FamilyMember receiver) {
        if(!matches.containsKey(giver.name)){
            matches.put(giver.name, receiver.name)
        }
    }

    @Override
    Boolean hasGiver(String receiver) {
        return matches.containsValue(receiver)
    }

    @Override
    Boolean hasReceiver(String giver) {
        return matches.containsKey(giver)
    }
}
