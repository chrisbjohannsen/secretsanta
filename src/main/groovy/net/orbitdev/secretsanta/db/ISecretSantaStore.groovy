package net.orbitdev.secretsanta.db

import net.orbitdev.secretsanta.domain.FamilyMember

/**
 * Secret santa match store definition
 */
interface ISecretSantaStore {

    void addMatch(FamilyMember giver, FamilyMember receiver)
    Boolean isReceiver(String receiver)
    Boolean isGiver(String giver)
}