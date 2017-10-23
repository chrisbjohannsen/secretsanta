package net.orbitdev.secretsanta.db

import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.domain.SecretSantaMatch

import java.time.ZonedDateTime
import java.util.concurrent.ConcurrentHashMap


/**
 * In memory, thread safe implmentation of ISecretSantaStore
 * The Store is a Key/Value store where the key is the integer id of the familyMember
 * and the value is an set of matches that familyMember is a member
 */
class InMemorySecretSantaStore implements ISecretSantaStore {

    final Object lock = new Object()
    ConcurrentHashMap<Integer, Set<SecretSantaMatch>> matches = [:]

    /**
     * In order to keep the matches in sync we have to add a new match record
     * for both the giver key and the receiver key. This does result in data duplication but reduces
     * the number of records that must be searched when evaluating if a FamilyMember has a match
     * @param giver
     * @param receiver
     */
    @Override
    void addMatch(FamilyMember giver, FamilyMember receiver) {
        def newMatch = new SecretSantaMatch(giver: giver, receiver: receiver, matchDate: ZonedDateTime.now())

        synchronized (lock) {

            if (!matches.containsKey(giver.id)) {
                def matchSet = new HashSet<SecretSantaMatch>()
                matchSet << newMatch
                matches.put(giver.id, matchSet)
            } else {
                Set<SecretSantaMatch> records = matches.get(giver.id)
                records << newMatch
                matches.replace(giver.id, records)
            }

            if (!matches.containsKey(receiver.id)) {
                def matchSet = new HashSet<SecretSantaMatch>()
                matchSet << newMatch
                matches.put(receiver.id, matchSet)
            } else {
                Set<SecretSantaMatch> records = matches.get(receiver.id)
                records << newMatch
                matches.replace(receiver.id, records)
            }

        }
    }

    /**
     * True if the passed in name exists as a value in the store
     */
    @Override
    Boolean isReceiver(FamilyMember receiver) {
        Set<SecretSantaMatch> matchRecords = matches.get(receiver.id)
        return matchRecords.any { it.receiver.id == receiver.id }
    }

    /**
     * True if passed in name is a key in the store
     */
    @Override
    Boolean isGiver(FamilyMember giver) {
        Set<SecretSantaMatch> matchRecords = matches.get(giver.id)
        return matchRecords.any { it.giver.id == giver.id }
    }

    @Override
    SecretSantaMatch[] getMatches(int memberId) {
        return matches.get(memberId)
    }
}
