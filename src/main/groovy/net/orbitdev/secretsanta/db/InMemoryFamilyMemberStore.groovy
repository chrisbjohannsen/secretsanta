package net.orbitdev.secretsanta.db

import net.orbitdev.secretsanta.domain.FamilyMember

/**
 * In memory, non duplicate implementation of the IFamilyMemberStore
 */
class InMemoryFamilyMemberStore implements IFamilyMemberStore {

    //use a Set to prevent duplicates
    Set<FamilyMember> familyMembers = []

    void addMember(FamilyMember memberToAdd){
        this.familyMembers << memberToAdd
    }

    @Override
    Set<FamilyMember> getMembers() {
        return this.familyMembers
    }

    @Override
    FamilyMember getRandomMember() {
        familyMembers[new Random().nextInt(familyMembers.size())]
    }
}
