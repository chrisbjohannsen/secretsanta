package net.orbitdev.secretsanta.db

import net.orbitdev.secretsanta.domain.FamilyMember

/**
 * FamilyMember store definition
 */
interface IFamilyMemberStore {
    void addMember(FamilyMember memberToAdd)
    Set<FamilyMember> getMembers()
    FamilyMember getRandomMember()
}