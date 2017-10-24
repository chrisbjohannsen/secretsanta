package net.orbitdev.secretsanta.db

import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.domain.SecretSantaMatch

/**
 * Secret santa match store definition
 */
interface ISecretSantaStore {

    /**
     * Add a match to the store
     * @param giver
     * @param receiver
     */
    void addMatch(FamilyMember giver, FamilyMember receiver)

    /**
     * Tests if a family member is in a match as a receiver
     * @param receiver
     * @return True or False
     */
    Boolean isReceiver(FamilyMember receiver)

    /**
     * Tests if a family member is in a match as a giver
     * @param giver
     * @return True or False
     */
    Boolean isGiver(FamilyMember giver)

    /**
     * Returns a list of matches for a family member specified by id
     * @param memberId
     * @return an array of SecretSantaMatch
     */
    SecretSantaMatch[] getMatches(int memberId)
}