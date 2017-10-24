package net.orbitdev.secretsanta.db

import net.orbitdev.secretsanta.domain.FamilyMember

/**
 * FamilyMember store definition
 */
interface IFamilyMemberStore {
    /**
     * Adds a family member to the store
     * @param memberToAdd
     */
    void addMember(FamilyMember memberToAdd)

    /**
     * Fetch the members from the store
     * @return
     */
    Set<FamilyMember> getMembers()

    /**
     * Fetch a randomly selected member from the store
     * @return
     */
    FamilyMember getRandomMember()
}