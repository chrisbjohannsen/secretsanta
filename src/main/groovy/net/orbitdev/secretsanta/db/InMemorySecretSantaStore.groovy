package net.orbitdev.secretsanta.db

import net.orbitdev.secretsanta.domain.FamilyMember

import java.util.concurrent.ConcurrentHashMap


/**
 * In memory, thread safe implmentation of ISecretSantaStore
 */
class InMemorySecretSantaStore implements ISecretSantaStore {

    ConcurrentHashMap matches = [:]

    @Override
    void addMatch(FamilyMember giver, FamilyMember receiver) {
        synchronized (matches){
            if (!matches.containsKey(giver.name)) {
                matches.put(giver.name, receiver.name)
            }
        }
    }

    /**
     * True if the passed in name exists as a value in the store
     */
    @Override
    Boolean isReceiver(String receiver) {
        return matches.containsValue(receiver)
    }

    /**
     * True if passed in name is a key in the store
     */
    @Override
    Boolean isGiver(String giver) {
        return matches.containsKey(giver)
    }
}
