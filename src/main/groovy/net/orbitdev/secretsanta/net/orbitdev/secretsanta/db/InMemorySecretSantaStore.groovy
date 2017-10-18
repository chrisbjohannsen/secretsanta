package net.orbitdev.secretsanta.net.orbitdev.secretsanta.db

class InMemorySecretSantaStore implements ISecretSantaStore {

    Map matches = [:]

    @Override
    void addMatch(String giver, String receiver) {
        if(!matches.containsKey(giver)){
            matches.put(giver, receiver)
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
