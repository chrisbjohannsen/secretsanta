package net.orbitdev.secretsanta.net.orbitdev.secretsanta.db

import net.orbitdev.secretsanta.domain.FamilyMember

class FamilyMemberStore {

    FamilyMember[] familyMembers = []

    void addMemeber(FamilyMember memberToAdd){
        this.familyMembers << memberToAdd
    }
}
