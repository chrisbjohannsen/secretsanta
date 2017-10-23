package net.orbitdev.secretsanta.db

import net.orbitdev.secretsanta.domain.FamilyMember
import net.orbitdev.secretsanta.domain.SecretSantaMatch

/**
 * Secret santa match store definition
 */
interface ISecretSantaStore {

    void addMatch(FamilyMember giver, FamilyMember receiver)
    Boolean isReceiver(FamilyMember receiver)
    Boolean isGiver(FamilyMember giver)
    SecretSantaMatch[] getMatches(int memberId)
}