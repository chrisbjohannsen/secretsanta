package net.orbitdev.secretsanta.net.orbitdev.secretsanta.db

import net.orbitdev.secretsanta.domain.FamilyMember

class InMemoryFamilyMemberStore implements IFamilyMemberStore {

    //use a Set to prevent duplicates
    Set<FamilyMember> familyMembers = []

    void addMember(FamilyMember memberToAdd){
        this.familyMembers << memberToAdd
    }

    @Override
    FamilyMember[] getMembers() {
        return this.familyMembers
    }

    @Override
    FamilyMember getRandomMember() {
        familyMembers[new Random().nextInt(familyMembers.size())]
    }
}
