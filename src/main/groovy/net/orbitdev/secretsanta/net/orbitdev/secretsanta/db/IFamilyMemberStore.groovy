package net.orbitdev.secretsanta.net.orbitdev.secretsanta.db

import net.orbitdev.secretsanta.domain.FamilyMember

interface IFamilyMemberStore {
    void addMember(FamilyMember memberToAdd)
    FamilyMember[] getMembers()
    FamilyMember getRandomMember()
}